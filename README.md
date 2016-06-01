Node: [![Dependency Status](https://www.versioneye.com/user/projects/57279c34a0ca35004cf7629b/badge.svg?style=flat)](https://www.versioneye.com/user/projects/57279c34a0ca35004cf7629b)  
Bower: [![Dependency Status](https://www.versioneye.com/user/projects/57279c1fa0ca35004baf7509/badge.svg?style=flat)](https://www.versioneye.com/user/projects/57279c1fa0ca35004baf7509)   
SBT: [![Dependency Status](https://www.versioneye.com/user/projects/57279c29a0ca350034be6303/badge.svg?style=flat)](https://www.versioneye.com/user/projects/57279c29a0ca350034be6303)   
Gradle: [![Dependency Status](https://www.versioneye.com/user/projects/572a7198a0ca35005084077d/badge.svg?style=flat)](https://www.versioneye.com/user/projects/572a7198a0ca35005084077d)   
Play: [![Compilation Status](https://travis-ci.org/shiroverlord/Portail-Auto-Eval.svg?branch=master)](https://travis-ci.org/shiroverlord/Portail-Auto-Eval)


This is a seed Play application with :

Gradle (replaced default SBT), Hibernate (+ HikariCp), NodeJs, Gulp , Bower, and AngularJS
=========================================================================================

Heroku configuration included
=============================
Add followings Environnement Variables:

'JAVA_OPTS': '-Dhttp.port=${PORT} -Dconfig.resource=heroku.conf    -Xss512k -XX:+UseCompressedOops)'

'JAVA_TOOL_OPTIONS': '-Dhttp.port=${PORT} -Dconfig.resource=heroku.conf    -Xss512k -XX:+UseCompressedOops)'

(Think to add Database pour this project in Heroku -> a new Variable will be created 'DATABASE_URL')

Travis configuration included
=============================
Add followings Environnement Variables:

'HEROKU_API_KEY': 'THE_API_KEY_IN_HEROKU'

What you need to begin: 
- JAVA JDK 8
- GIT

AND:
- Activator (Or Gradle)
- NodeJs
- Bower

OR use Gradlew which will install what you need.

Some commands to use with Gradle (or Gradlew) are described in 'build.gradle' file.
