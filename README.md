This is my Hadoop MapReduce starter program running in Docker environment. It is a mini tool implemented searching files based on keywords.<br/> 

**The accuracy of the results can be adjusted by changing the THRESHOLD variable(currently 10) in EngineReducer.java**<br/>
![res3](https://cloud.githubusercontent.com/assets/22739177/21577060/a0df58c8-cf01-11e6-99cb-aa3406ee2085.PNG)

**Transfer files from local file system to HDFS:**<br/>
hadoop fs -mkdir -p hdfs://hadoop-master:9000/ebooks<br/>
hdfs dfs -put ebooks/* hdfs://hadoop-master:9000/ebooks<br/>
hadoop fs -mkdir -p hdfs://hadoop-master:9000/sw<br/>
hdfs dfs -put stopwords.txt hdfs://hadoop-master:9000/sw<br/>

**Check files in HDFS:**<br/>
hdfs dfs -ls hdfs://hadoop-master:9000/<br/>
![res6](https://cloud.githubusercontent.com/assets/22739177/21577062/a0e17270-cf01-11e6-8f47-68db9c5dbfb7.PNG)

**Run the project:**<br/>
hadoop jar MiniSearchEngine.jar com.minisearchengine.mapreduce.EngineDriver hdfs://hadoop-master:9000/ebooks hdfs://hadoop-master:9000/results //hadoop-master:9000/sw/stopwords.txt<br/>
![res1](https://cloud.githubusercontent.com/assets/22739177/21577059/a0df450e-cf01-11e6-9c76-0ddb97cb1aeb.PNG)

**Transfer results from HDFS to local file system:**<br/>
hdfs dfs -get hdfs://hadoop-master:9000/results/part-r-* ./results/<br/>

**Now we get all the keywords of specific files:**<br/>
![res2](https://cloud.githubusercontent.com/assets/22739177/21577057/a0df427a-cf01-11e6-837c-9f0949beb4b3.PNG)

**Search Examples:**<br/>
&nbsp;&nbsp;Search file(s) based on keyword "abbey"<br/>
![res4](https://cloud.githubusercontent.com/assets/22739177/21577058/a0df5e5e-cf01-11e6-9fdb-76cb610c7b3c.PNG)
&nbsp;&nbsp;Search file(s) based on keyword "frightened"<br/>
![res5](https://cloud.githubusercontent.com/assets/22739177/21577061/a0dfb99e-cf01-11e6-9110-a57ed8c16849.PNG)
