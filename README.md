# IOD-LO4-ALPHA
# Project description

BuildingInfo is a specialized application, created for those who would like to optimize building management costs. 

# Input data structure:

In order to make correct calculations user needs to provide correct file in JSON format:

```
{
  "building": {
    "id": <int>,
    "name": <string>,
    "levels": [
      {
        "id": <int>,
        "name": <string>,
        "rooms": [
          {
            "id": <int>,
            "name": <string>,
            "area": <float>,
            "cube": <float>,
            "heating": <float>,
            "light": <float>
          }
        ]
      }
    ]
  }
}
```
It is possible to load multiple floors and rooms in one file.

# Running application:

To run application user needs to run the command in the directory which contains .jar file:
```
java -jar io-project-architecture-X.Y.jar
```
X.Y - is the version of application

