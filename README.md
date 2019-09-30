# Library Management System Project

This is a CRUD application utilizing Java. 

## Data Persistence 

Upon initial load, an "lmsdata" folder is created with one file for each entity: "author.txt," "book.txt," and "publisher.txt." Each line in an entity file is a pipe delimited list of field values. The "Document" package contains wrappers for the Java file handling APIs to read and write these files.

## Data Access Layer

The "dao" package contains 1 data access class for each entity type: "AuthorDao," "BookDao," and "PublisherDao." These classes use the Document package to perform the low level entity-specific logic required to read and write their assigned entity type.

## Service Layer

The "service" package contains 1 service class for each entity type: "AuthorService," "BookService," and "PublisherService." These classes utilize the dao package to perform the business logic associated with inserting, querying, updating, and deleting their assigned entity types. For example, the BookService class will not allow a book to be inserted if its author or publisher does not exist.

## View Layer

This "view" package contains the View interface and classes that implement it. A View interacts directly with the user, displaying prompts and reading user inputted data back into the application via the view's delegate. 

## Controller Layer

The "controller" package contains classes that implement the delegate interface of a view. These classes receive events from views (such as that a certain action was entered or a certain entity id was inputted for update) and use a service (via the "service" package) that corresponds to that event. Once the service is done, the controller layer chooses a view to tell the user what happened and how they can proceed. 








