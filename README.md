# alvex-orgchart

Alvex Orgchart for Alfresco allows creating the company structure specifying divisions, departments, subordinates and managers, and also the roles (positions) of employees inside the company unit. 
Company structure can be used to choose users for task allocation, to associate an employee with a document and automatic task delegation.

## Compatible Alfresco version

* Alfresco 5.1
* Alfresco 4.2 (use [this project](https://github.com/ITDSystems/alvex))

## Installation

This project is based on [Alfresco Maven SDK 2.2](http://docs.alfresco.com/5.1/concepts/alfresco-sdk-installing-prerequisite-software.html).
Build AMP with:

```
cd orgchart-repo-amp
mvn clean package
cd ../orgchart-share-amp
mvn clean package
```

Then install `orgchart-repo-amp/target/orgchart-repo-amp-1.0-SNAPSHOT.amp` and `orgchart-share-amp/target/orgchart-share-amp-1.0-SNAPSHOT.amp` to Alfresco.

Also you can start embedded Alfresco to test Orgchart using `./run.sh` for Linux and `run.bat` for Windows.

## Features

### Create Orgchart in Alfresco Share Admin Console

![Create Orgchart in Alfresco Share Admin Console](http://docs.alvexcore.com/en-US/Alvex/2.1/html-single/Admin_Guide/images/img6.png)

### Org Chart for users

![Org Chart for users](http://docs.alvexcore.com/en-US/Alvex/2.1/html-single/Admin_Guide/images/img21.png)

### Orgchart picker for Alfresco forms

![Orgchart picker for Alfresco forms](http://docs.alvexcore.com/en-US/Alvex/2.0.3/html-single/User_Guide/images/1_25.png)

### Assigning tasks by user roles

![Assigning tasks by user roles](http://www.itdhq.com/img/blog/2015-10-13/1.png)

### Out-of-Office 
![Out-of-office](http://docs.alvexcore.com/en-US/Alvex/2.0.3/html-single/User_Guide/images/3_16.png)

## Configuration

[Alvex documentation](http://docs.alvexcore.com/en-US/)
We will move Alvex Orgchart documentation here during some time.
