# fenxlib-widgets
> Custom JavaFX Widgets

Fenxlib-widgets is in auxiliary project to the fenxlib library.  Whereas fenxlib generally contains factories for build JavaFX native and JFoenix libraries, this library focusses on custom widgets.

## Table of Contents
- [Installation](#installation)
- [Features](#features)
- [License](#licensing)

## Installation
### Built With
* OpenJDK8
* OpenJFX8
* [JEXL3](http://commons.apache.org/proper/commons-jexl/)
* [Commons-lang3](http://commons.apache.org/proper/commons-lang/)
* fenxlib
* [TestFX](https://github.com/TestFX/TestFX)
* [JUnit](https://junit.org/junit4/)
* [Hamcrest](http://hamcrest.org/JavaHamcrest/)

### Prerequisites
* A Java8 JDK with JavaFX (Amazon Corretto, ZuluFX, etc)
 
### Clone and install into mavenLocal
```shell
git clone https://github.com/Legyver/fenxlib-widgets.git
cd fenxlib-widgets/
gradlew install
```
The last command installs the library into mavenLocal

### Importing fenxlib-widgets
 ```build.gradle
repositories {
    mavenLocal()
}
dependencies {
    compile group: 'com.legyver', name: 'fenxlib-widgets', version: '1.0.0.0'
}
```

### Running Tests
```shell
gradlew test
```

## Features
### OpenSourceReferenceList
Reads a properties file of licenses and links and renders to screen.
<p>license.properties</p>

```properties
com.jfoenix=Apache License 2.0
com.jfoenix.link.0=https://github.com/jfoenixadmin/JFoenix/blob/master/LICENSE
org.apache.logging.log4j=Apache License 2.0
org.apache.logging.log4j.link.0=https://logging.apache.org/log4j/2.0/license.html
commons-io=Apache License 2.0
commons-io.link.0=https://github.com/apache/commons-io/blob/master/LICENSE.txt
commons-codec=Apache License 2.0
commons-codec.link.0=https://github.com/apache/commons-codec/blob/master/LICENSE.txt
com.fasterxml.jackson.core.jackson-databind=Apache License 2.0
com.fasterxml.jackson.core.jackson-databind.link.0=https://github.com/FasterXML/jackson-databind/blob/master/LICENSE
io.icomoon=GPL/CC BY 4.0
io.icomoon.link.0=http://www.gnu.org/licenses/gpl.html
io.icomoon.link.1=http://creativecommons.org/licenses/by/4.0/
```
See the demo application in src/demo for an example.

### AboutPage
Display an about page based on properties files and preconfigured text.
```java
AboutPageOptions aboutPageOptions = new AboutPageOptions.Builder(getClass())
    .dependenciesFile("license.properties")
    .buildPropertiesFile("build.properties")
    .copyrightPropertiesFile("copyright.properties")
    .title("MyApplication")
    .intro("MyApplication makes amazing things easy")
    .gist("More stuff about how great this app is.  I can go on about it for a really long time and the text will wrap around.")
    .additionalInfo("be sure to tell your friends")
    .build();

BorderPaneInitializationOptions options = new BorderPaneInitializationOptions.Builder()
    .top()
    .factory(new TopRegionFactory(
        new RightMenuOptions(
            new MenuFactory("Help", new AboutMenuItemFactory("About", centerContentReference, aboutPageOptions))
        )
    )
```
build.properties
```properties
major.version=1
minor.version=0
patch.number=0

build.number=0000
build.date.day=11
build.date.month=April
build.date.year=2020

#Note: Expression must be a valid jexl3 expression.
#below ends up being injected into build.date
build.date.format=`${build.date.day} ${build.date.month} ${build.date.year}`

#below ends up being injected into build.version
build.version.format=`${major.version}.${minor.version}.${patch.number}.${build.number}`

#below ends up being injected into build.message displayed on about screen
build.message.format=`Build ${build.version}, built on ${build.date}`
```
copyright.properties
```properties
copyright.format=`Copyright © Legyver 2020-${build.date.year}.`
```
![image](https://user-images.githubusercontent.com/3435255/79179401-8fa9d700-7dd5-11ea-8590-81f0aaec0d4d.png)

See the demo application in src/demo for the full code.

## Licensing

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/Legyver/fenxlib-widgets/blob/master/LICENSE)

