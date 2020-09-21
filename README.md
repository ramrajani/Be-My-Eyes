### Overview
This is a camera app that continuously detects the objects (bounding boxes and classes) in the frames seen by your device's back camera, using a quantized [MobileNet SSD](https://github.com/tensorflow/models/tree/master/research/object_detection) model trained on the [COCO dataset](http://cocodataset.org/). These instructions walk you through building and running the demo on an Android device.

The model files are downloaded via Gradle scripts when you build and run. You don't need to do any steps to download TFLite models into the project explicitly.

Application can run either on device or emulator.

<!-- TODO(b/124116863): Add app screenshot. -->

### Model used
Downloading, extraction and placing it in assets folder has been managed automatically by download.gradle.

If you explicitly want to download the model, you can download from **[here](http://storage.googleapis.com/download.tensorflow.org/models/tflite/coco_ssd_mobilenet_v1_1.0_quant_2018_06_29.zip)**. Extract the zip to get the .tflite and label file.

### Additional Note
_Please do not delete the assets folder content_. If you explicitly deleted the files, then please choose *Build*->*Rebuild* from menu to re-download the deleted model files into assets folder.
