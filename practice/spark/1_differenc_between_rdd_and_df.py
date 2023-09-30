from pyspark import SparkContext
from pyspark.sql import SparkSession

father = {'name':'joesph', 'age':35}
mother = {'name':'jina', 'age':30}
son = {'name':'julian', 'age':15}

family = []
family.append(father)
family.append(mother)
family.append(son)

# 1. RDD type Spark Data
# sc = SparkContext(master='local[*]', appName = 'Spark App Python')
# rdd = sc.parallelize(family)
# print(rdd.collect())

# 2. RDD -> Spark DataFrame
spark = SparkSession.builder.master('local[*]').appName('Spark SQL Test Python').getOrCreate()
schema = ['age', 'name']
df = spark.createDataFrame(rdd, schema)

df.printSchema()
df.show()
