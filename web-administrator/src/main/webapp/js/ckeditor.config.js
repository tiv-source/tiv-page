CKEDITOR.editorConfig = function( config )
{

  config.allowedContent = true;
  config.protectedSource.push( /<ins[\s|\S]+?<\/ins>/g); // Protects <INS> tags
  config.protectedSource.push( /<ins class=\"adsbygoogle\"\>.*?<\/ins\>/g );

};