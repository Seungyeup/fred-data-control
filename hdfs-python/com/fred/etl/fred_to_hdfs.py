import configparser
import os
import time
from com.fred.etl.us_states import US_STATES

from fredapi import Fred
from pyarrow import fs


class FRED2HDFS(object):
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

        _api_ky = config['FRED_CONFIG']['api_key']
        self._fred = Fred(api_key=_api_ky)
        self._hdfs = fs.HadoopFileSystem('localhost', 9000)
        self._list_state = [state.value for state in US_STATES]

    def getFredDf(self, freq, state, str_search):
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

