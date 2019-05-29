# A5

----------
# Overview

This is a ragdoll application that uses a series of bitmap images (representing a man with wings riding a goose). There are squares drawn over each image. These are hitboxes for the various components and it makes it easier to move them.


![](https://paper-attachments.dropbox.com/s_72330075492C09DBFCEA3F180A46DDED61317CC293328E1B04CB98E7123B7FFF_1554337495092_Screen+Shot+2019-04-03+at+8.24.29+PM.png)
![](https://paper-attachments.dropbox.com/s_72330075492C09DBFCEA3F180A46DDED61317CC293328E1B04CB98E7123B7FFF_1554337495083_Screen+Shot+2019-04-03+at+8.24.11+PM.png)

## Important Note (Please read entirely)
The rectangles were made using the 3rd party polygon library (with permission): https://github.com/sromku/polygon-contains-point.
Rectangles were used as the hitboxes since the shape of the components were complex enough that it would've taken a substantial amount of time trying to match them with polygons. The rectangles are drawn over the images to make the hitboxes more discernable (**this is on purpose**).

## Components

The mapping of components to the original ragdoll is as follows:

**Torso →** Man + goose


**Head →** The hat


**Arms →** Wings (2 segments)


**Hands →** Wing tips


**Legs →** Goose legs


**Feet** → Goose feet



There is a layering system. In the case of overlap for hit detection,

- arms/legs take precedence over torso
- legs take precedence over arms
- hands/feet take precedence over arms/legs
- feet take precedence over hands.


## Requirements

As per: https://www.student.cs.uwaterloo.ca/~cs349/w19/assignments/a5.html
The following items have been implemented (**using the above mapping of components**):

- The torso is the only body part that can be translated.
  - Touching and dragging the torso should move the entire paper doll. The torso *cannot* be rotated.
- All other body parts can be rotated around their contact point.
  - The head can tilt left and right relative to the torso, but should not deviate more than 50 degrees in either direction from the primary axis defined by the torso. You may define where the rotation point is for the head (i.e. at the torso, or some other location, such as in the head).
  - The upper arm is attached to the torso and may rotate an entire 360 degrees about its point of attachment to the torso. When rotating the upper arm, the lower arm should retain its same relative orientation to the upper arm. For example, if the lower arm is at a 30 degree angle relative to the upper arm, and the upper arm is rotated, the lower arm should retain its 30 degree angle relative to the upper arm.
  - The lower arm should have a movement range of 135 degrees in either direction relative to the primary axis defined by the upper arm.
  - The hand can pivot 35 degrees in either direction relative to the lower arm. It should maintain its same relative orientation to the lower arm independent of any rotations of the lower or upper arm.
  - The upper leg can pivot 90 degrees in either direction relative to the primary axis defined by the torso.
  - The lower leg can also pivot 90 degrees in either direction relative to primary axis defined by the upper leg.
  - Assume the feet are attached at a 90 degree angle to the lower leg. Given this, they can pivot 35 degrees in either direction from this initial orientation.
- Upper and lower legs can be scaled.
  - The upper and lower legs can each be "stretched" by scaling them along their primary axes. Other parts, such as the feet, however, should stay at the same scale (i.e., feet do not scale). Furthermore, scaling the upper leg should scale the corresponding lower leg simultaneously.

