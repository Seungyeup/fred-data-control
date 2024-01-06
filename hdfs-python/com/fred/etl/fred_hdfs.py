import configparser
import os
import time
from com.fred.etl.us_states import US_STATES

from fredapi import Fred
from pyarrow import fs


class Fred2Hdfs(object):
    """
    classics
    """

    def __init__(self):
        """
        Constructor
        """
        config = configparser.ConfigParser()

        path = '/Users/lsy/Desktop/fred-data-control/hdfs-python/com/fred/etl'
        os.chdir(path)
        config.read('resources/SystemConfig.ini')

        _api_key = config['FRED_CONFIG']['api_key']
        self._fred = Fred(api_key=_api_key)
        self._hdfs = fs.HadoopFileSystem('localhost', 9000)
        self._list_state = [state.value for state in US_STATES]

    def get_fred_df(self, freq, state, str_search):
        str_search_text = str_search + state
        print(str_search_text)
        df_fred_col = self._fred.search(str_search_text)

        time.sleep(0.5)

        if df_fred_col is None:
            return None

        mask_df = ((df_fred_col.title == str_search_text)
                   & (df_fred_col.frequency_short == freq)
                   & (df_fred_col.seasonal_adjustment_short == 'NSA'))

        df_fred_col = df_fred_col.loc[mask_df, :]
        df_fred_col['state'] = state

        if not df_fred_col.empty:
            df_etl_col = self._fred.get_series(df_fred_col.iloc[0].id)
            time.sleep(0.7)

            df_etl_col = df_etl_col.to_frame(name='values')
            df_etl_col['realtime_start'] = df_fred_col.iloc[0].realtime_start
            df_etl_col['realtime_end'] = df_fred_col.iloc[0].realtime_end
            df_etl_col['state'] = df_fred_col.iloc[0].state
            df_etl_col['id'] = df_fred_col.iloc[0].id
            df_etl_col['title'] = df_fred_col.iloc[0].title.replace(',', '_')
            df_etl_col['frequency_short'] = df_fred_col.iloc[0].frequency_short
            df_etl_col['units_short'] = df_fred_col.iloc[0].units_short
            df_etl_col['seasonal_adjustment_short'] = df_fred_col.iloc[0].seasonal_adjustment_short

            return df_etl_col

    def get_list_fred_df(self, freq, title):
        df_list = map(lambda state: self.get_fred_df(freq, state, title), self._list_state)
        df_list = list(filter(lambda df: df is not None, df_list))

        return df_list

    def write_csv_2_hdfs(self, filename, df_data):
        with self._hdfs.open_output_stream(filename) as native_fs:
            line = df_data.to_csv(index_label='date')
            print(line)
            native_fs.write(line.encode())

    def append_csv_2_hdfs(self, filename, df_data):
        with self._hdfs.open_append_stream(filename) as native_fs:
            line = df_data.to_csv(header=False)
            print(line)
            native_fs.write(line.encode())

    @staticmethod
    def clear_input_files(fdir, fname):
        path = fdir + "/" + fname

        if os.path.isfile(path):
            os.remove(path)
            print("{}를 삭제하였습니다.".format(path))

