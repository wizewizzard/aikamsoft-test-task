# Aikamsoft test task

A test task for a job apply for Aikamsoft.com company.


## Data and inputs/outputs

#### Data

Data is randomly generated.

Consists of:
- 11 **users** (11th user has 0 purchases)
- 10 **products**
- 250 **purchases** ranged from *2021-07-13* until *2022-08-10*

#### Inputs
Inputs are `*.json` files that split into two groups for **search** and **stat** operations.
They are located in the root of the project and get copied by `maven-antrun-plugin` into `./target` directory

Set of **search** inputs located in `search-inputs`:
- `input1.json` - a simple input that demonstrates that all criterias work and their ordering is not changed in `output.json`
- `input2.json` - an input that is filled with values that are not present in database, so the `output.json` is filled with blank results.
- `input3.json` - an input that demonstrates that app criterias can be duplicated
- `input4.json` - `input7.json` - are inputs that trigger an error

Set of **stat** inputs located in `stat-inputs`:
- `input1.json` - embraces the whole period if purchases (with weekends excluded)
- `input2.json` - one last month period (with weekends excluded)
- `input3.json` - just one day with one purchase
- `input4.json` - `input5.json` - are inputs that trigger an error
- `input6.json` - an input that results into a blank `output.json` because dates are not present in the database

## Run Locally

Clone the project

```bash
  git clone git@github.com:wizewizzard/aikamsoft-test-task.git
```

Go to the project root directory.
#### Database

There is a database dump in the root directory. One can manually import it into a local database or use Docker instead.

If you have Docker installed then `dump.sql` can be automatically imported into the containerized database. To do this run
```bash
  docker build -t psql-with-dump-aikamsoft .
```
And to run the image in a container
```bash
  docker run -d -e POSTGRES_PASSWORD=admin -e POSTGRES_USER=aikamsoft-task-db -p 5432:5432 psql-with-dump-aikamsoft
``` 
The database is ready.
#### Application
In order to build the project run
```bash
  mvn clean package -DskipTests
```
Go to the `./target` directory, where `test-task.jar` is located

```bash
  cd ./target
```

Now you can run application using the `.jar` file

To perform `search` operation use inputs from `./search-inputs` directory

```bash
  DB_URL=jdbc:postgresql://localhost:5432/aikamsoft-task-db DB_USER=aikamsoft-task-db DB_PASSWORD=admin JSON_PRETTY=true java -jar test-task.jar search './search-inputs/input1.json' 'output.json'
```
To perform `stat` operation use inputs from `./stat-inputs` directory

```bash
  DB_URL=jdbc:postgresql://localhost:5432/aikamsoft-task-db DB_USER=aikamsoft-task-db DB_PASSWORD=admin JSON_PRETTY=true java -jar test-task.jar stat './stat-inputs/input1.json' 'output.json'
```

The environment variables are the following

- `DB_URL` - JDBC database URL
- `DB_USER`, `DB_PASSWORD` - database username and password
- `JSON_PRETTY` - whether the `output.json` has to be pretty formatted. Default is **TRUE**

Arguments are
- Operation name `search` and `stat` are available
- Input file
- Output file
