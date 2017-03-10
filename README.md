[![DOI](https://zenodo.org/badge/69028129.svg)](https://zenodo.org/badge/latestdoi/69028129)

Introduction
============
Within this archive you find the replication package for the paper "Global vs. Local Models for Cross-project Defect Prediction - A replication study" by Steffen Herbold, Alexander Trautsch, and Jens Grabowski accepted for publication by the Empirical Software Engineering Journal, Springer. The aim of this replication package is to allow other researchers to replicate our results with minimal effort. 

Requirements
============
- Java 8.
- JDK and Ant for compilation of the source code. 

Contents
========
This folder contains:
- Three subfolders exp-* that contain the data, experiment configurations, and results for the three data sets that are used.
- The compiled crosspare.jar file for the execution of the experiments. 
- The scripts runexperiments-linux.sh and runexperiments-windows.bat for the execution of the experiments under Linux and Windows.
- The source code of CrossPare from which the crosspare.jar was compiled (from Nov 2014, git commit ID 7a45562017de196c38eac668a804034c744fdc7b). Please note that the development of CrossPare has continued since that point in time and the latest version is available on [GitHub](https://github.com/sherbold/CrossPare/).
- The build.xml for building CrossPare using Ant.
- The XML schema of the experiment configurations (required by the Ant build).
- The lib folder with the referenced Java libraries.
- The additional-results folder contains the results achieved with the random forest.  


CrossPare
=========
We executed our experiment using the tool [CrossPare](https://github.com/sherbold/CrossPare/). CrossPare implements many approaches proposed for cross-project defect prediction and supports loading data from various published data sets. If possible, CrossPare does not re-implement anything. This is enabled by building CrossPare around the [Weka](https://weka.wikispaces.com/) machine learning library. CrossPare wraps the sampling, machine learning, and classifier evaluation of Weka. This wrapping is necessary due to the peculiarities of the data treatment for cross-project defect prediction. The features used within this replication are all re-using Weka functionality wrapped within CrossPare, with two exceptions which were not possible with the features provided by Weka: 
- the WHERE clustering was re-implemented as described in the paper.
- the KNN relevancy filter.

How does it work?
=================
To execute the replication, you can simply use the batch scripts for Linux and Windows. Please note that they try to create a virtual machine with access to 8 GB of heap space. This may fail on your machine. In that case you need to modify the script to use less heap space, and modify it such that only it has only only one configuration file is called at a time. To do this, you have to replace the program arguments with a specific configuration file (see below) instead of all three config folders. But it may happen that you run out of heap space and the experiment execution crashes, if you use less than 8 GB.

**Executing the replication overwrites the original results that you downloaded!**

The replication package uses the tool [GitHub](https://github.com/sherbold/CrossPare/). CrossPare implements many approaches proposed for cross-project defect prediction and supports loading data from various published data sets. The experiments themselves are defined using XML files, that describe which data is loaded and how the defect prediction models are trained. Each experiment folder contains a config folder with the files.
- US.xml: Training of the GLOBAL, EM, and WHERE models with the SVM and with normalization and undersampling (US).
- US-ENTITYKNN.xml: Training of the KNN model (ENTITYKNN) with the SVM and with normalization and undersampling (US).
- NONORM-US.xml: Training of EM and WHERE models with the SVM and without normalization (NONORM) and with undersampling (US).

The crosspare.jar gets the paths to the config folders as parameters and loads all the experiment configurations. Then, these experiment configurations are executed. The experiments themselves all only use a single CPU. However, CrossPare is implemented in such a way that all CPU cores a utilized by executed multiple experiments in parallel. 

The results of the execution are stored in the results and results-rf folders within the exp-* folders. Each folder contains one  Comma Separated Value (CSV) file per experiment. Since each experiment may train and evaluate multiple classifiers at once, the CSV files contain the results for all classifiers within an experiment. Each column within the file marks itself the results for which metric and which classifier it contains. The column names follow the structure metricname_classifier. As classifiers, we have:
- *_SMORBF for the results of the GLOBAL model and the SVM.
- *_SMORBFLocalEM for the results of the EM model and the SVM.
- *_SMORBFLocalFQ for the results of the WHERE model and the SVM.


For example, the column recall_SMORBF in the file US.csv is the recall of the GLOBAL model with the SVM, where normalization and undersampling are used. 

The CSV files contain more metrics than are reported on within the paper, among others:
- recall
- precision
- error rate
- F-measure (fscore column)
- G-measure (gscore column)
- Area Under the Curve (auc column)
- Matthews Correlation Coefficient (mcc column)
- true positive rate (same as recall)
- true negative rate
- the confusion matrix itself (tp, fp, tn, fn columns).

**There are several other columns within the file as well, however, they should be ignored as they are experimental and where not tested properly**. Most of them result rather from testing what is possible with CrossPare and where hacked in rather quickly without any double checking, e.g., AUCEC to see if the evaluation framework would be able to take effort into account. However, this works only for some data sets, and even there buggy. *Please also not that this comment holds for the version of CrossPare contained in the replication package. Further development since May 2015 may have fixed, removed or added the calculation of metrics.*

Additional Results Folder
=========================
We included the some of the results of our internal experiments with the random forest, which, unfortunately could not make it into the paper due to space restrictions (it is quite long anyways). 
- RF-JSTAT-US.csv contains the results for the GLOBAL, EM, and WHERE on the JSTAT data.
- RF-JSTAT-US-ENTITYKNN.csv contains the results for KNN on the JSTAT data.
- RF-MDP-US.csv contains the results for GLOBAL, EM, and WHERE on the MDP data.
- RF-MDP-US-ENTITYKNN.csv contains the results for KNN on MDP data.
- RF-JPROC-US.csv contains the results for GLOBAL, EM, and WHERE on the JPROC data.
- RF-JPROC-US-ENTITYKNN.csv contains the results for KNN on the JPROC data.

The results columns follow the same naming conventions as described above. The classifier names for the columns are:
- *_RF for the results of the GLOBAL model and the RF.
- *_RFLocalEM for the results of the EM model and the RF.
- *_RFLocalFQ for the results of the WHERE model and the RF.

Building CrossPare from Source
==============================
The source code and libraries for CrossPare are provided within the replication kit. The crosspare.jar file can be built using the provided Ant script by calling "ant dist". The build results will then appear in the folder "dist".  


License
=======
This replication package as well as the CrossPare software that is used are licensed under the Apache License, Version 2.0. 
