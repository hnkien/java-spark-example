package io.example.sql;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import java.util.ArrayList;
import java.util.List;

public class InMemoryData {

    public static void main(String[] args) {
        new InMemoryData().run();
    }

    public void run() {
        Logger.getLogger("org.apache").setLevel(Level.WARN);

        try (SparkSession sparkSession = SparkSession
                .builder()
                .appName("StartingSpark")
                .master("local[*]")
                .getOrCreate()) {
            List<Row> inMemory = new ArrayList<>();
            inMemory.add(RowFactory.create("WARN", "2016-12-31 04:19:32"));
            inMemory.add(RowFactory.create("FATAL", "2016-12-31 03:22:34"));
            inMemory.add(RowFactory.create("WARN", "2016-12-31 03:21:21"));
            inMemory.add(RowFactory.create("INFO", "2015-4-21 14:32:21"));
            inMemory.add(RowFactory.create("FATAL", "2015-4-21 19:23:20"));

            StructField[] fields = new StructField[]{
                    new StructField("level", DataTypes.StringType, false, Metadata.empty()),
                    new StructField("datetime", DataTypes.StringType, false, Metadata.empty()),
            };
            StructType schema = new StructType(fields);

            Dataset<Row> dataset = sparkSession.createDataFrame(inMemory, schema);
            dataset.show();
        }
    }
}
