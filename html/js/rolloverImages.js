// rolloverImages.js
//
// functions for easily adding rollover images to existing images on the page


// rollover internal variables
var test_img = new Image(5,5);
var off_images = new Array(test_img);
var on_images = new Array(test_img);
var roll_count = 0;
var ie_roll = ((document.all)? true : false);
var moz_roll = ((document.all)? false : true);

// *********************************************************************************
// addRollover( document_image_name, rollover_image_file, image_width, image_height )
// - add a rollover image to a named image object on the page 
// - note*: uses the 'id' parameter of the source image object 
// - return: <none>
// ***************************************************** 
function addRollover(img_name, rollover_img_src, width, height) 
{
  var fullname = "document." + img_name;
  var imgobj = eval(fullname);

  var imgObjON = new Image(width,height);
  imgObjON.src = rollover_img_src;
  var imgObjOFF = new Image(width,height);
  imgObjOFF.src = imgobj.src;
  off_images = off_images.concat( imgObjOFF );
  on_images = on_images.concat( imgObjON );
  roll_count = roll_count + 1;

  if (moz_roll) {
     imgobj.addEventListener('mouseover', rollby, false);
     imgobj.addEventListener('mouseout', rollout, false);
  }
  if (ie_roll) {
     imgobj.attachEvent('onmouseover',rollby);
     imgobj.attachEvent('onmouseout',rollout);
  }
 
  imgobj.id = roll_count;
}


// ****************************************************************************
// rollby( event )
// - event listener for MOUSEOVER
// ******************************
function rollby(evt)
{
  rollOver(evt.target.name,evt.target.id,1);
}

// ****************************************************************************
// rollout( event )
// - event listener for MOUSEOUT
// *****************************
function rollout(evt)
{
  rollOver(evt.target.name,evt.target.id,0);
}

// ****************************************************************************
// rollOver( image_element_name, rollover_id, rollover_state )
// - state=0 : default
// - state=1 : rollover
// ********************
function rollOver(name, id, state)
{
  var newImg = new Image(1,1);
  if (state==0) {
    newImg = off_images[id];
  } else {
    newImg = on_images[id];
  }
  window.document[name].src = newImg.src;
}
