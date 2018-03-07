function initClick()
{
    var objects = document.getElementsByTagName("img");
    for(var i=0;i<objects.length;i++)
    {
    var img = objects[i];
    img.style.maxWidth = '100%'; img.style.height = 'auto';
        objects[i].onclick= function (){
            window.JavaScriptFunction.getUrl(this.src);
        }
    }
}