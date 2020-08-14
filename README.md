# postman2jmx-with-envs
Postman collection to Jmeter jmx file converter.

Fork of [postman2jmx-accso](https://github.com/fredericjacob/postman2jmx-accso), which is fork of original [postman2jmx](https://github.com/Loadium/postman2jmx). 

Just changed some lines in main function for passing path to JSON file with Postman environments when running jar. In postman2jmx-accso paths to envs files were hardcoded (from repo's README.md: *Adapted version from the original converter, tailored to Accso's DSO project.*)

### Note

- postman2jmx converter only converts the Postman V2+ exported files!

### Installation

postman2jmx requires Java8+ and Maven 3+.

- Clone or download the project.
```sh
$ git clone https://github.com/Ar3q/postman2jmx-with-envs
```
- Build the project.
```sh
$ cd postman2jmx-with-envs
$ mvn package
```

### Usage

- After build, go to the Postman2Jmx folder. It is located under the target folder.
Otherwise the converter will not work.
```sh
$ cd target/Postman2Jmx
```
- Then execute the following command to convert your postman json collection file to the jmx file.
```sh
$ java -jar Postman2Jmx.jar my_postman_collection.json my_postman_environments.json my_jmx_file.jmx
```

License
----

The MIT License (MIT)
