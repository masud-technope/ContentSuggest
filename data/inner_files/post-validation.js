'use strict';StackExchange.postValidation=function(){function v(b,a,c){var d=b.find('input[type\x3d"submit"]:visible'),k=d.length&&d.is(":enabled");k&&d.attr("disabled",!0);A(b);B(b,a,c);C(b);D(b);E(b);var e=function(){1!=a||b.find(p).length?(F(b),k&&d.attr("disabled",!1)):setTimeout(e,250)};e()}function w(){for(var b=0;b<f.length;b++)clearTimeout(f[b]);f=[]}function A(b){var a=b.find(r);a.length&&a.blur(function(){f.push(setTimeout(function(){var c=a.val(),d=$.trim(c);if(0==d.length)e(b,a);else{var k=
a.data("min-length");k&&d.length<k?h(a,[function(a){return 1==a.minLength?"Title must be at least "+a.minLength+" character.":"Title must be at least "+a.minLength+" characters."}({minLength:k})],g()):(k=a.data("max-length"))&&d.length>k?h(a,[function(a){return 1==a.maxLength?"Title cannot be longer than "+a.maxLength+" character.":"Title cannot be longer than "+a.maxLength+" characters."}({maxLength:k})],g()):$.ajax({type:"POST",url:"/posts/validate-title",data:{title:c},success:function(d){d.success?
e(b,a):h(a,d.errors.Title,g())},error:function(){e(b,a)}})}},l))})}function B(b,a,c){var d=b.find(s);d.length&&d.blur(function(){f.push(setTimeout(function(){var k=d.val(),f=$.trim(k);0==f.length?e(b,d):5==a?(k=d.data("min-length"))&&f.length<k?h(d,[function(a){return"Wiki Body must be at least "+a.minLength+" characters. You entered "+a.actual+"."}({minLength:k,actual:f.length})],g()):e(b,d):1!=a&&2!=a||$.ajax({type:"POST",url:"/posts/validate-body",data:{body:k,oldBody:d.prop("defaultValue"),isQuestion:1==
a,isSuggestedEdit:c},success:function(a){a.success?e(b,d):h(d,a.errors.Body,g())},error:function(){e(b,d)}})},l))})}function F(b){var a=b.find(p);if(a.length){var c=a.parent().find("input#tagnames");c.blur(function(){f.push(setTimeout(function(){var d=c.val();0==$.trim(d).length?e(b,a):$.ajax({type:"POST",url:"/posts/validate-tags",data:{tags:d,oldTags:c.prop("defaultValue")},success:function(d){d.success?e(b,a):h(a,d.errors.Tags,g())},error:function(){e(b,a)}})},l))})}}function C(b){var a=b.find(q);
a.length&&a.blur(function(){f.push(setTimeout(function(){var c=a.val(),c=$.trim(c);if(0==c.length)e(b,a);else{var d=a.data("min-length");d&&c.length<d?h(a,[function(a){return 1==a.minLength?"Your edit summary must be at least "+a.minLength+" character.":"Your edit summary must be at least "+a.minLength+" characters."}({minLength:d})],g()):(d=a.data("max-length"))&&c.length>d?h(a,[function(a){return 1==a.maxLength?"Your edit summary cannot be longer than "+a.maxLength+" character.":"Your edit summary cannot be longer than "+
a.maxLength+" characters."}({maxLength:d})],g()):e(b,a)}},l))})}function D(b){var a=b.find(t);a.length&&a.blur(function(){f.push(setTimeout(function(){var c=a.val(),c=$.trim(c);if(0==c.length)e(b,a);else{var d=a.data("min-length");d&&c.length<d?h(a,[function(a){return"Wiki Excerpt must be at least "+a.minLength+" characters; you entered "+a.actual+"."}({minLength:d,actual:c.length})],g()):(d=a.data("max-length"))&&c.length>d?h(a,[function(a){return"Wiki Excerpt cannot be longer than "+a.maxLength+
" characters; you entered "+a.actual+"."}({maxLength:d,actual:c.length})],g()):e(b,a)}},l))})}function E(b){var a=b.find(u);a.length&&a.blur(function(){f.push(setTimeout(function(){var c=a.val();0==$.trim(c).length?e(b,a):/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,20}$/i.test(c)?e(b,a):h(a,["This email does not appear to be valid."],x())},l))})}function g(){var b=$("#sidebar, .sidebar").first().width()||270;return{position:{my:"left top",at:"right center"},css:{"max-width":b,"min-width":b},closeOthers:!1}}
function x(){return{position:{my:"left top",at:"right center"},css:{"min-width":$("#sidebar, .sidebar").first().width()||270},closeOthers:!1}}function y(b,a,c,d){if(b){var k=function(){var c=0;h(a.find(r),b.Title,g())?c++:e(a,a.find(r));h(a.find(s),b.Body,g())?c++:e(a,a.find(s));h(a.find(p),b.Tags,g())?c++:e(a,a.find(p));h(a.find(q),b.EditComment,g())?c++:e(a,a.find(q));h(a.find(t),b.Excerpt,g())?c++:e(a,a.find(t));h(a.find(u),b.Email,x())?c++:e(a,a.find(u));var m=a.find(".general-error"),k=b.General&&
0<b.General.length;if(k||0<c)if(m.length||(a.find('input[type\x3d"submit"]:visible').before('\x3cdiv class\x3d"general-error-container"\x3e\x3cdiv class\x3d"general-error"\x3e\x3c/div\x3e\x3cbr class\x3d"cbt" /\x3e\x3c/div\x3e'),m=a.find(".general-error")),k)h(m,b.General,{position:"inline",css:{"float":"left","margin-bottom":"10px"},closeOthers:!1,dismissable:!1});else{e(a,m);switch(d){case "question":c=function(a){return 1==a.specificErrorCount?"Your question couldn't be submitted. Please see the error above.":
"Your question couldn't be submitted. Please see the errors above."}({specificErrorCount:c});break;case "answer":c=function(a){return 1==a.specificErrorCount?"Your answer couldn't be submitted. Please see the error above.":"Your answer couldn't be submitted. Please see the errors above."}({specificErrorCount:c});break;case "edit":c=function(a){return 1==a.specificErrorCount?"Your edit couldn't be submitted. Please see the error above.":"Your edit couldn't be submitted. Please see the errors above."}({specificErrorCount:c});
break;case "tags":c=function(a){return 1==a.specificErrorCount?"Your tags couldn't be submitted. Please see the error above.":"Your tags couldn't be submitted. Please see the errors above."}({specificErrorCount:c});break;default:c=function(a){return 1==a.specificErrorCount?"Your post couldn't be submitted. Please see the error above.":"Your post couldn't be submitted. Please see the errors above."}({specificErrorCount:c})}m.text(c)}else a.find(".general-error-container").remove();var f;z()&&($("#sidebar").animate({opacity:0.4},
500),f=setInterval(function(){z()||($("#sidebar").animate({opacity:1},500),clearInterval(f))},500));var n;a.find(".validation-error").each(function(){var a=$(this).offset().top;if(!n||a<n)n=a});m=function(){for(var b=0;3>b;b++)a.find(".message").animate({left:"+\x3d5px"},100).animate({left:"-\x3d5px"},100)};n?(c=$(".review-bar").length,n=Math.max(0,n-(c?125:30)),$("html, body").animate({scrollTop:n},m)):m()},f=function(){1!=c||a.find(p).length?k():setTimeout(f,250)};f()}}function h(b,a,c){if(a&&0!=
a.length&&b.length&&$("html").has(b).length)a=1==a.length?a[0]:"\x3cul\x3e\x3cli\x3e"+a.join("\x3c/li\x3e\x3cli\x3e")+"\x3c/li\x3e\x3c/ul\x3e";else return!1;if(a&&0<a.length){var d=b.data("error-popup");if(d&&d.is(":visible")){if(b.data("error-message")==a)return!0;d.fadeOutAndRemove()}c=StackExchange.helpers.showMessage(b,a,c);c.find("a").attr("target","_blank");b.addClass("validation-error");b.data("error-popup",c);b.data("error-message",a);c.click(w);return!0}return!1}function e(b,a){if(!a||0==
a.length)return!1;var c=a.data("error-popup");c&&c.is(":visible")&&c.fadeOutAndRemove();a.removeClass("validation-error");a.removeData("error-popup");a.removeData("error-message");b.find(".validation-error").length||b.find(".general-error-container").remove();return!0}function z(){var b=!1,a=$("#sidebar, .sidebar").first();if(!a.length)return!1;var c=a.offset().left;$(".message").each(function(){var a=$(this);if(a.offset().left+a.outerWidth()>c)return b=!0,!1});return b}var r="input#title",s="textarea.wmd-input:first",
p=".tag-editor",q="input[id^\x3dedit-comment]",t="textarea#excerpt",u="input#m-address",f=[],l=250;return{initOnBlur:v,initOnBlurAndSubmit:function(b,a,c,d,e){v(b,a,d);var f,l=function(d){if(d.success)if(e)e(d);else{var h=window.location.href.split("#")[0],g=d.redirectTo.split("#")[0];0==g.indexOf("/")&&(g=window.location.protocol+"//"+window.location.hostname+g);f=!0;window.location=d.redirectTo;h.toLowerCase()==g.toLowerCase()&&window.location.reload(!0)}else d.captchaHtml?StackExchange.captcha.init(d.captchaHtml,
l):d.errors?y(d.errors,b,a,c):b.find('input[type\x3d"submit"]:visible').parent().showErrorMessage(d.message)};b.submit(function(){if(b.find("#answer-from-ask").is(":checked"))return!0;var a=b.find(q);if("[Edit removed during grace period]"==$.trim(a.val()))return h(a,["Comment reserved for system use. Please use an appropriate comment."],g()),!1;w();StackExchange.navPrevention&&StackExchange.navPrevention.stop();b.find('input[type\x3d"submit"]:visible').parent().addSpinner();StackExchange.helpers.disableSubmitButton(b);
setTimeout(function(){$.ajax({type:"POST",dataType:"json",data:b.serialize(),url:b.attr("action"),success:l,error:function(){var a;switch(c){case "question":a="An error occurred submitting the question.";break;case "answer":a="An error occurred submitting the answer.";break;case "edit":a="An error occurred submitting the edit.";break;case "tags":a="An error occurred submitting the tags.";break;default:a="An error occurred submitting the post."}b.find('input[type\x3d"submit"]:visible').parent().showErrorMessage(a)},
complete:function(){StackExchange.helpers.removeSpinner();f||StackExchange.helpers.enableSubmitButton(b)}})},0);return!1})},showErrorsAfterSubmission:y,getSidebarPopupOptions:g}}();