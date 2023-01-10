## _OOP TASK 2_
_Authors [orel-dayan@]() & [@roy-asraf1](https://github.com/roy-asraf1)_

### Built With

* Editor : IntelliJ IDEA
* Language: Java
* JDK: 19.0.1
* JUnit 5.9.0

### Getting Start

clone this repository 
```sh
https://github.com/orel-dayan/OOP-TASK2.git
```
### Ex2_1
Run the main function and choose the number of files and max number of lines in the file in terminal.                                                               
To delete files you may use the next commands.
```sh
   cd <repository folder>/src
   rm file*
   ```
### Ex2_2
You can see and run the example in the Tests class.

## PART A:
This project provides a set of utility functions for working with text files. we create text files and calculate the total number of lines in these files.  
### We will use three methods: ####  

• Normal method without the use of threads

• Using of threads

• Using ThreadPool



### EX2_1:
A class that contains 4 functions:
- createTextFiles
- getNumOfLines
- getNumOfLinesThreads
- getNumOfLinesThreadPool

### thread:
A process (`thread`) is defined as a series of commands, which are executed serially. In a multi-process program it is possible to define several processes that are executed at the same time.
### When to use
- When we want a more "natural" solution to a problem by dividing the problem into subproblems, each of which is realized by
Another thread.
- When we want a fast response time to our input in real time.
- When we want to improve the running time of some algorithm.

### Threadpool:
A `Threadpool` is a collection of worker threads that can be used to execute tasks. When a task is submitted to the thread pool, a worker `thread` is selected to execute the task. When the task is completed, the worker `thread` returns to the pool to wait for another task. Thread pools are useful because they allow you to execute multiple tasks concurrently while limiting the number of threads that are actively executing at any given time. This can help to improve the performance and scalability of your program.
### When to use
- When you have a large number of short-lived tasks that need to be executed concurrently, a thread pool can be more efficient than creating and destroying a new `thread` for each task.
- When you want to limit the number of concurrent threads executing at any given time, you can use a thread pool to control the number of active threads.
- When you want to schedule tasks to be executed at a later time, you can use a thread pool to manage the execution of those tasks.
- When you want to execute tasks in a specific order, you can use a thread pool to ensure that tasks are executed in the desired sequence.

### Important Information:
It is important to note that using a thread pool is not always the best solution. In some cases, creating a new `thread` for each task may be more benefit. It is also important to consider the trade-offs between using a thread pool and using other concurrent execution frameworks, such as the fork-join framework or the actors model.

## Classes:
### numOfLinesThreadPool class
`numOfLinesThreadPool` is a class that implements the `Callable` interface and defines a new task that can be submitted to a thread pool.The class has a constructor that accepts a `String` name parameter and has a `call()` method that reads a file and counts the number of lines in it.The `call()` method returns the number of lines in the file as an `Integer` value. This value will be returned by the thread pool when the task is complete.
### numOfLinesThreads class
`numOfLinesThreads` is a class that extends the `Thread` class and defines a new thread.The class has a constructor that accepts a `String` name parameter and passes it to the `super` constructor. It also has a run() method that reads a file and counts the number of lines in it. the class has a getCount() method that returns the value of a counter variable that is incremented each time a line is read from the file.

## Usage
To use Ex2_1 class to Create several text files and calculate the total number of lines in these files. first insert number to the 2 next objects: numberOfFiles and maxNumberOfLines, later Implement the function with the previues objects with number to seed. After that it will automaticliiy create a new files, and will print:
- The number of lines with The time is took.
- The number of lines using Threads with The time is took.
- The number of lines using ThreadPool with The time is took.

### Conclusions
In general, using a thread pool is a good choice for concurrent processing when you have a large number of tasks to be processed. It allows you to take advantage of concurrent processing while minimizing the overhead of managing multiple threads. However, for small numbers of tasks, the overhead of creating a thread pool may outweigh the benefits of concurrent processing. In such cases, using separate threads or processing the tasks sequentially may be more efficient.

<table>
  <tr>
    <th>Method</th>
    <th> Time (ms)</th>
    <th>Seed, Bound</th>
    <th>Number of Files</th>
    <th>Total Num of Lines</th>
  </tr>
  <tr>
    <td>getNumOfLines</td>
    <td></td>
    <td>2, 1001 </td>
    <td>1000</td>
    <td></td>
  </tr>
    <td>getNumOfLinesThreads</td>
    <td></td>
    <td>2, 1001 </td>
    <td>1000</td>
    <td></td>
  </tr>
  <tr>
    <td>getNumOfLinesThreadPool</td>
    <td></td>
    <td>2, 1001 </td>
    <td>1000</td>
    <td></td>
  </tr>
</table>


<table>
  <tr>
    <th>Method</th>
    <th> Time (ms)</th>
    <th>Seed, Bound</th>
    <th>Number of Files</th>
    <th>Total Number of Lines</th>
  </tr>
  <tr>
    <td>getNumOfLines</td>
    <td></td>
    <td>2, 999</td>
    <td>1000</td>
    <td></td>
  </tr>
    <td>getNumOfLinesThreads</td>
    <td></td>
    <td>2, 999 </td>
    <td>1000</td>
    <td></td>

  <tr>
    <td>getNumOfLinesThreadPool</td>
    <td></td>
    <td>2, 999 </td>
    <td>1000</td>
    <td></td>
  </tr>
</table>




## UML
![image](https://user-images.githubusercontent.com/117816462/210965665-a864cb80-8162-43d9-930d-958d9d7a470d.png)


# Part B:
