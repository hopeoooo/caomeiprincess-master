$(function () {
    initSkin();
});
function initSkin() {
    if(localStorage.getItem("caomei_skin")){
       $(".el-menu").css("background",localStorage.getItem("caomei_skin"));
    }else{
        $(".el-menu").css("background","#9D2E7D");
    }
}

function logout(){
    window.open("/admin/logout","_self");
}
var colorArr = ["#6DE195","#C4E759","#41C7AF","#54E38E",
    "#99E5A2","#D4FC78","#ABC7FF","#C1E3FF",
    "#6CACFF","#8DEBFF","#5583EE","#41D8DD",
    "#A16BFE","#DEB0DF","#D279EE","#F8C390",
    "#F78FAD","#FDEB82","#BC3D2F","#A16BFE",
    "#A43AB2","#E13680","#9D2E7D","#E16E93"];
var arrIndex = 0;
var isRun = false;
var interval;
function skin(){
    isRun=!isRun;
    if(isRun){
        interval = setInterval(function(){
            if(arrIndex >colorArr.length-1) {
                arrIndex = 0;
            }
            $(".el-menu").css("background",colorArr[arrIndex]);
            localStorage.setItem("caomei_skin",colorArr[arrIndex]);
            arrIndex++;
        }, 2000);
        $("#Switch").text("Stop Switch");
    }else{
        clearInterval(interval);
        $("#Switch").text("Start Switch");
    }
}