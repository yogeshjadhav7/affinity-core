# Affinity-Core

### Description
----
"Affinity" because it reveals plausible affinities between the **entities** and **terms** in the data. "Core" because it lies silently at the core of the systems needing plausible relationships from the data to make them smarter and more personalized to the end-users.

### What are Entities, Terms and Weights?
---
**Entities**, **Terms** and **Weights** form the basis of the input file in Affinity-Core. As in the conventional structure of the input data file of a simple (conventional) problem statement in **data science**, the columns are the features, the rows are the instances (data points) and the value in ith row of jth column represents the value of feature j for instance i. Following this as our generic example, entities can be the set of i's (provided that the each row corresponds to an unique entity) and terms can be the set of j's. 

Let's be more specific to understand better. Suppose you have precise recipes of various preparations (carrot cake, potato salad, french fries etc) with the exact amount of each ingredients by weight. Exactly similar like **affinity-session-data/Recipes-12345/input.txt**. Project **Recipes-12345** uses preparations as entities, ingredients as terms and ingredient amounts (grams) as weights. These recipes are scrapped from https://www.chefsteps.com/.

### Use case
---
You can fit endless number of use cases in this generic structure of the input file. Like, if you want to explore on the plausible affinity realtionship between the deseases and their symptoms - parse medical E-books/journals/blogs or by some other way to get the count of the each symptom mentioned while describing each desease. In this case, deseases become entities, symptoms become terms and count of each symptom mentions become weights. The output files of the process will reveal plausible affinity scores of each entity-term pair in **AFFINITY_CALCULATION.txt**. More on the output files below.

### Input File Structure
---
There is only one data (input) file that is required by the process, along with few mandatory and optional arguments / parameters. The structure of the input CSV file is **Entity Name, Term Name, Weight** on each line. You can refer **affinity-session-data/Recipes-12345/input.txt** where preparations are entities, ingredients are terms and ingredient amounts by weight (grams) are weights.


### Data Flow
---
1. The input file is parsed and the data fetched is validated.
2. The data flows in processing module from validation module. Here, following steps are performed to process the raw data.
    - Building of **PPMI matrix** from the sparse data.
    - Building of **Angular-Difference** matrix from the PPMI matrix.
    - Building the **Distance Matrix** from Angular-Difference matrix converting sparse data matrix to dense matrix.
    - Finally, building of **MDS Matrix** from the dense Distance Matrix.
3. MDS matrix is used to perform Affinity calculation which relate each entity-term pair with an affinity score.
4. The decision boundary of linear SVM computed for each term to separate entities is used in calculation of bonding scores between the terms. More on the output files below.


### Output Files
---
As the process starts, directory corresponding to your current project (session name and session id) is created inside **affinity-session-data/**. The naming convention of the project directory is **sessionName-sessionId**. This directory will house your output files. As the data flow through various modules (mentioned in the **Data Flow** section above), output files for each process will be generated in the project directory. Two important files to focus on are **AFFINITY-CALCULATION.txt** and **TERM_BOND_CALCULATION.txt** which houses computed metrics to relate aspects of the input data. Other files like **AFFINITY.txt**, **INPUT_PROCESSING.txt** and **INPUT_VALIDATION.txt** are the checkpoint files which aids in supporting **warm_start** nature in Affinity-Core.

The content in all the output files is in prettified JSON format.

> **AFFINITY-CALCULATION.txt**
This file houses the computed affinity scores for each entity-term pair. Affinity scores of a term for all entities reveals how strong the plausible relationship is between the entities and that term. 

> **TERM_BOND_CALCULATION.txt**
This file houses very interesting bonding scores between the terms. **Bonding score reveals the likelihood of a term being used together with another term in the context of the domain of the data**. For example, if the domain of input data is recipes as mentioned above, TERM_BOND_CALCULATION.txt will reveal the likelihood of using other ingredients together with a given ingredient in the preparation. Refer **affinity-session-data/Recipes-12345/TERM_BOND_CALCULATION.txt**. 
Another (more useful) application of the term bonding scores, if the domain of input data is medical where entities are deseases and terms as symptoms, TERM_BOND_CALCULATION.txt will reveal the likelihood of getting other symptoms given a person is already showing a symptom. **Cool isn't it !?**


### Installation Prerequisites
----
> Java 1.8

> Install Maven on your system. (https://maven.apache.org/install.html)


### Installation
----
> Clone the repository.
``` sh
git clone https://github.com/yogeshjadhav7/affinity-core.git
```

> Go to the root directory of the project.
``` sh
cd affinity-core/
```

> Install **libs/libsvm.jar and libs/mdsj.jar** on your system for Maven builds. These jars don't exist in any public repository like Maven Central and hence they have to be installed on the system explicitly for Apache Maven to identify them as Maven projects/dependencies. 
**NOTE:** This is an important steps as we have specified the same as our project dependencies in **pom.xml**. You can tweak groupId, artifactId and version in following Maven install commands as you like, given that you do the corresponding tweaks in dependencies mentioned in **pom.xml** as well.
```sh
mvn install:install-file -Dfile=libs/libsvm.jar -DgroupId=libsvm -DartifactId=libsvm -Dversion=1.0.0 -Dpackaging=jar
```
```sh
mvn install:install-file -Dfile=libs/mdsj.jar -DgroupId=mdsj -DartifactId=mdsj -Dversion=1.0.0 -Dpackaging=jar
```

> Test the installation. This will also download and install Maven essentials on your system, if Maven was not already installed on your system.
```sh
mvn clean package
```
If everything goes right, successful building of **target/affinity-core-jar-with-dependencies.jar** gives you all-dependencies-in-one jar to use Affinity-Core as Command-Line API or as a dependency in your Machine Learning project.

### Command Line Arguments
---
There are two types of arguments to be passed to Affinity-Core Command Line API.
> **Mandatory Arguments**
1. **sessionName** : The name should be your task/project name for which you want these plausible affinity relationships between entities and terms in the data. 
2. **sessionId** : This integer value should be unique for the sessionName as to keep track of the quality of the affinity scores after tweaking various parameters and hyper-parameters of Affinity-Core iteratively. A simple iteration starting from 1, keeping the same sessionName for the same project would be ideal!
3. **inputFile** : The location of the input file which follows the above mentioned structure.

> **Optional Arguments (Affinity-Core Configuration)**
1. **configScaleLength** : Scale length is an integer value used for acaling affinity scores computed in AFFINITY_CALCULATION.txt output file.
2. **configClassificationJob** : A boolean flag in the configuration which specifies the type of the job. If equals to true, affinity scores in AFFINITY_CALCULATION.txt will be provided after performing softmax (computing probabilities for terms) for each entity.
3. **configMdsDimensions** : An integer value specifies the number of dimensions of the multidimensions space, wherein process computes decision boundary for a linear kernel SVM for each term to divide entities. This value can also be described as number of features (dimensionality) considered by the process for each entities. There is a tradeoff between high accuracy (high MDS dimensions) and low computation speed (low MDS dimensions).

### Command Line Usage
> At the root directory, run the java command to execute **target/affinity-core-jar-with-dependencies.jar** by providing mandatory and optional arguments. We would be using nohup to run the process in background.
``` sh
nohup java -jar target/affinity-core-jar-with-dependencies.jar sessionId=1 sessionName=MyFirstAffinityCoreProject inputFile=/home/yogesh/some-input.txt configScaleLength=100 configClassificationJob=true configMdsDimensions=20 &
```

### Project Usage
> To make use of Affinity-Core in your ML project, add the jar located at **target/affinity-core-jar-with-dependencies.jar** in the classpath of your project. 
**Step #1** : Create Session object by providing sessionName and sessionId to the constructor.
```sh
import co.in.vertexcover.affinity.client.session.Session;
// other imports

final String sessionId = "1";
final String sessionName = "MyFirstAffinityCoreProject";
final Session session = new Session(sessionName, sessionId);
```

> **Step #2** : Start the process on the session object created in Step #1 by providing the location of the input file. 
```sh
import co.in.vertexcover.affinity.client.dto.Configurations;
// other imports

final String inputFileLocation = "/home/yogesh/some-input.txt";
session.startProcess(inputFileLocation);
```

> **(Optional) Step #2** : You could also tweak the configuration parameters  if you are not happy with default values.  Start the process on the session object created in Step #1 by providing the location of the input file and the configuration object.
```sh
import co.in.vertexcover.affinity.client.dto.Configurations;
// other imports

final String inputFileLocation = "/home/yogesh/some-input.txt";

final Configurations configurations = new Configurations();
configurations.setMDS_DIMENSIONS(20);
configurations.setDoClassification(true);
configurations.setSCALE_LENGTH(100);

session.startProcess(inputFileLocation, configurations);
```

### Inspiration
This project is inspired from the published research paper **"Inducing semantic relations from conceptual spaces: A data-driven approach to plausible reasoning" - Joaquín Derrac, Steven Schockaert**.



