package com.hdfs.etl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoredColumnPojo implements Serializable {

    private Date date;
    private float value;
    private String id;
    private String title;
    private String state;
    private String frequency_short;
    private String units_short;
    private String seasonal_adjustment_short;

    // DataFrame 의 Schema 객체를 생성
    private static StructType structType = DataTypes.createStructType(new StructField[]{
            DataTypes.createStructField("date", DataTypes.DateType, false),
            DataTypes.createStructField("value", DataTypes.FloatType, false),
            DataTypes.createStructField("id", DataTypes.StringType, false),
            DataTypes.createStructField("title", DataTypes.StringType, false),
            DataTypes.createStructField("state", DataTypes.StringType, false),
            DataTypes.createStructField("frequency_short", DataTypes.StringType, false),
            DataTypes.createStructField("units_short", DataTypes.StringType, false),
            DataTypes.createStructField("seasonal_adjustment_short", DataTypes.StringType, false),
    });

    public static StructType getStructType() {
        return structType;
    }

    public Object[] getAllValues(){
        return new Object[] {
                date,value,id,title,state,frequency_short,units_short,seasonal_adjustment_short
        };
    }

}
