# fuzzy-octo-telegram

## Add new model

### Generate Model + Migration
```
$ cd fuzzy-octo-telegram/src/main/java/fuzzy/orm/shell/
$ bash generateModel.sh model=SampleModel String=name boolean=isReady double=runtime
```

### Run Migration
```
$ cd fuzzy-octo-telegram/src/main/java/migrations
$ javac SampleModelMigration.java
$ java SampleModelMigration
```

## Model Operations

### Find entity by id

```java
int id = 5;
SampleModel model = (SampleModel) SampleModel
                .find(id)
                .invoke();
```

### Find first created entity

```java
int id = 5;
SampleModel model = (SampleModel) SampleModel
                .select()
                .order(Model.ID, "DESC")
                .invoke();
```

### Find last created entity

```java
int id = 5;
SampleModel model = (SampleModel) SampleModel
                .select()
                .order(Model.ID, "ASC")
                .invoke();
```

### Create entity

```java
SampleModel model = (SampleModel) SampleModel
                .insert()
                .insertProp("name", "Unknown")
                .invoke();
```

### Update entity

```java
// Get a model instance
SampleModel model = // ...;
// Update                
model.update()
     .insertProp("name", "Unknown")
     .invoke();
```

### Delete entity

```java
// Get a model instance
SampleModel model = // ...;
// Delete                
model.delete().invoke();
```
