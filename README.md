
# react-native-head-less-js

## Getting started

`$ npm install react-native-head-less-js --save`

### Mostly automatic installation

`$ react-native link react-native-head-less-js`

### Manual installation


#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNHeadLessJsPackage;` to the imports at the top of the file
  - Add `new RNHeadLessJsPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-head-less-js'
  	project(':react-native-head-less-js').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-head-less-js/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-head-less-js')
  	```


## Usage
```javascript
import RNHeadLessJs from 'react-native-head-less-js';

// TODO: What to do with the module?
RNHeadLessJs;
```
  