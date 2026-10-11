# Lab Thread Counter: Static vs. Non-Static Variables in Java

## 1. Experiment Title

**Comparison of Static and Non-Static Variables in Java Multithreading**

## 2. Objectives

The main objectives of this experiment are:

- To understand the differences between static and non-static variables in Java.
- To observe how multiple threads access and modify shared variables.
- To compare thread-safe and non-thread-safe counter operations.
- To identify race conditions and lost updates in multithreaded programs.
- To calculate the absolute and percentage differences between static and non-static counter values.

## 3. Tools and Technologies

- Programming Language: Java
- Development Environment: Visual Studio Code
- Compiler: Java Development Kit (JDK)
- Operating System: Windows
- Version Control: Git and GitHub

## 4. Project Structure

```text
Lab_Thread_Counter/
├── Moynul_Thread.java
├── README.md
└── outputs/
    ├── TC1_safe.png
    ├── TC1_unsafe.png
    ├── TC2_safe.png
    └── TC2_unsafe.png
```

The `Moynul_Thread.java` file contains the Java implementation, while the `outputs` directory stores screenshots of the actual terminal results.

## 5. Experimental Description

This experiment compares static and non-static counters under different multithreading conditions.

A static variable belongs to the class and is shared among all instances of that class. Therefore, multiple threads accessing the same static counter can interfere with one another if updates are not properly synchronized.

A non-static variable belongs to an individual object. Each thread in this experiment operates on a separate object with its own instance counter. After all threads finish execution, the instance counter values are added together to calculate the total.

The program supports two execution modes:

- **Thread-safe mode:** Uses Java's `AtomicLong` to perform atomic counter increments.
- **Unsafe mode:** Uses the `++` operator on a shared static variable without synchronization. Concurrent updates may be lost because the increment operation is not atomic.

## 6. Compilation and Execution

### Compile the Java Program

Run the following command in the VS Code terminal:

```bash
javac Moynul_Thread.java
```

### Execute the Program

The general command format is:

```bash
java Moynul_Thread <threads> <increments> <true|false>
```

Parameters:

- `threads`: Number of threads to create.
- `increments`: Number of increments performed by each thread.
- `true`: Enables thread-safe counter operations.
- `false`: Enables unsafe counter operations.

### Example Commands

```bash
java Moynul_Thread 1 1000 true
java Moynul_Thread 1 1000 false
java Moynul_Thread 2 10000 true
java Moynul_Thread 2 10000 false
```

## 7. Experimental Results

The expected total count is calculated using:

**Expected Count = Number of Threads × Increments per Thread**

The absolute difference is calculated using:

**Absolute Difference = |Static Count − Non-Static Total|**

The percentage difference is calculated using:

**Percentage Difference = (Absolute Difference / Non-Static Total) × 100**

When the non-static total is zero, the percentage difference requires special handling to avoid division by zero.

### Test Case 1: One Thread, 1,000 Increments

| Measurement | Thread-Safe Mode | Unsafe Mode |
|---|---:|---:|
| Number of threads | 1 | 1 |
| Increments per thread | 1,000 | 1,000 |
| Expected count | 1,000 | 1,000 |
| Static count | 1,000 | 1,000 |
| Non-static total | 1,000 | 1,000 |
| Absolute difference | 0 | 0 |
| Percentage difference | 0.0000% | 0.0000% |

**Observation:** Both modes produced the expected result because only one thread was executing the counter operations. There was no concurrent access from multiple threads.

### Test Case 2: Two Threads, 10,000 Increments per Thread

| Measurement | Thread-Safe Mode | Unsafe Mode |
|---|---:|---:|
| Number of threads | 2 | 2 |
| Increments per thread | 10,000 | 10,000 |
| Expected count | 20,000 | 20,000 |
| Static count | 20,000 | 15,416 |
| Non-static total | 20,000 | 20,000 |
| Absolute difference | 0 | 4,584 |
| Percentage difference | 0.0000% | 22.9200% |

**Observation:** The thread-safe mode produced the expected count of 20,000. In unsafe mode, the observed static count was 15,416, which was 4,584 less than the non-static total. This result demonstrates the possibility of lost updates when multiple threads increment the same shared variable without synchronization.

*Note: The unsafe result is an actual observed result from one execution. It may differ between runs because thread scheduling and timing can change.*

## 8. Output Screenshots

The `outputs` directory contains terminal screenshots showing the program's actual results for each test case.

| Screenshot | Description |
|---|---|
| `TC1_safe.png` | Test Case 1 in thread-safe mode |
| `TC1_unsafe.png` | Test Case 1 in unsafe mode |
| `TC2_safe.png` | Test Case 2 in thread-safe mode |
| `TC2_unsafe.png` | Test Case 2 in unsafe mode |

Additional screenshots will be added as the remaining test cases are completed.

## 9. Result Analysis

The experiment illustrates the importance of thread safety in concurrent programming.

In Test Case 1, both the thread-safe and unsafe implementations produced the correct result. Since only one thread was used, concurrent modification of the shared static variable did not occur.

In Test Case 2, the thread-safe implementation maintained the correct static count of 20,000. However, the unsafe implementation produced a lower count of 15,416. The difference occurred because multiple threads attempted to update the same shared variable concurrently.

The `++` operator involves reading, modifying, and writing a value. Without synchronization, these steps can overlap between threads, causing one update to overwrite another. This behavior is known as a **race condition**, and the resulting missing increments are called **lost updates**.

The non-static total remained correct in these tests because each thread used a separate object with its own counter, and the program summed the counters after all threads had completed.

## 10. Conclusion

This experiment demonstrates that static and non-static variables behave differently in Java multithreading.

Static variables are shared across instances of a class, making thread safety essential when multiple threads modify the same variable. Non-static variables belong to individual objects and can avoid direct counter interference when each thread uses a separate object.

The experimental results show that `AtomicLong` provides reliable atomic increments for the shared counter, whereas an unsynchronized static counter can produce incorrect results under concurrent execution.

Therefore, developers should use appropriate synchronization mechanisms when multiple threads access and modify shared mutable data.

## 11. Future Work

The experiment will be extended by testing the following thread counts:

- 1 thread
- 2 threads
- 5 threads
- 10 threads
- 20 threads
- 50 threads
- 100 threads

The specified increment counts for each test case will be used. Unsafe mode will be executed at least five times per thread count to observe variations in the results.

The final report will include all required test results, repeated unsafe-mode observations, screenshots, a comparison table, and a complete analysis of the findings.
