# QuodAI Challenge
**Coding challenge**

This project will build a small program that downloads GitHub data within 7 days and calculates a health score for open source projects hosted on GitHub. The scores shall be outputted to a simple CSV file.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

## Command Runnning Task
**Root Folder**: Assignment

```gradle run --args="[datetime_start] [datetime_end]"```

**Example**

```gradle run --args="2019-09-01T00:00:00Z 2019-09-07T00:00:00Z"```

After running the program, output will be the top **1000 projects** ranked by healthiest to least healthy and we will can access to a file named **health_scores.csv.**

## Health Metric
- Average number of commits (push) per day (to any branch)

## Technical decisions
### Built With
* **[Gradle]**(https://gradle.org/) - Dependency Management

*Benefit*:
>Gradle automates these tasks, minimizing the risk of humans making errors while building the software manually and separating the work of compiling and packaging our code from that of code construction.


* **[Gson]** - Java library

*Benefit*:
<br></br>
>* Gson can be used to convert Java Objects into their JSON representation. It can also be used to convert a JSON string to an equivalent Java object. 

>* In this project, I downloaded a random file to analyse before writing the program. The file was represented by JSON format, thus, I need to convert it into Java Object before executing process.

* **[Jackson]** - Java library

*Benefit*:
<br></br>
>* Jackson is a very popular and efficient java based library to serialize or map java objects to JSON and vice versa.

>* In this project, I used this library to read and write output data.

* **[ExecutorService]** -  Framework provided by the JDK 
<br></br>
>This program applied executorService which is capable of executing tasks concurrently in the background. This makes all downloaded GitHub archive events form https://www.gharchive.org/ does not crash after lauching. 

* **[Stream Processing]**

I executed all calculations to create required metric based on streaming method because:
<br></br>
  >* Some data naturally comes as a never-ending stream of events. To do batch processing, you need to store it, stop data collection at some time and processes the data.

  >* Batch processing lets the data build up and try to process them at once while stream processing process data as they come in hence spread the processing over time.

  >* Sometimes data is huge and it is not even possible to store it. Stream processing let you handle large fire horse style data and retain only useful bits.

### Improvement

* In this project, I carried out processing on my local machine. Therefore, it's a little subjective to analyse and execute because the memory and CPU varies on individuals. This solution is the program should be set up optional environment by users depending on their computer machines. 

* Additionally, when I ran my program I found that if my internet was not stable then my program did not go on. Thus, if there is a method to improve this problem then the program will be better.

## Author

* **Quach Mai Boi** - *Initial work* - [QuodAI Challenge](https://github.com/Tayerquach/quodAI_Assignment)

## References
* **LoganPhan** - https://github.com/LoganPhan/QuoAI-challange-2



