<?php 
session_start();
$TOTAL_ITEM=5;
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Welcome to <a href="http://www.stackoverflow.com">StackOverflow</a> User Study</title>
<link type="text/css" href="css/style.css" rel="stylesheet" />
<script src="https://google-code-prettify.googlecode.com/svn/loader/run_prettify.js"></script>
<style type="text/css">
code{
	font: 1em 'Courier New', Courier, Fixed, monospace;
    font-size : 100%;
    color: #000;
	background-color:#efefef;
    /*background : #fff url(http://www.estudiowas.com.ar/images/preback.jpg) no-repeat left top;*/
    overflow : auto;
    text-align:left;
    /*border : 1px solid #5581C0;*/
	padding : 3px;
    margin:1em 0 1em 0;
    line-height:20px;
    font-weight:normal!important;
	 }
 code, pre
 {
 background-color:#efefef;
 }


</style>

<script language="javascript" type="text/javascript">
var showother=function(ansid)
{
	var chkbox=document.getElementById("chk"+ansid+"-9");
	var textbox=document.getElementById("feed"+ansid);
	//alert("chk"+ansid+"-9");
	if(chkbox.checked==true)
	{
	//alert(textbox);
	textbox.style.display="block";
	//alert(textbox.style.display);
	}
	else
	{
		textbox.style.display="none";
	}
}
var makedisable=function()
{
	var submitbtn=document.getElementById("submit");
	submitbtn.setAttribute("disabled","disabled");
	return true;
	
}


</script>


</head>
<body>

<table border="0"  width="1000" align="center">
<tr>
<td>
<div class="header">
<h1>Welcome to StackOverflow User Study!!</h1>
<h2>Software Research Lab, University of Saskatchewan</h2>
</div>
</td>
</tr>
<tr>
<td>&nbsp;</td>
</tr>

<tr>
<td>
<form  action="" method="post">
<table border="0" cellpadding="2" cellspacing="2" width="1000px">
<tr>
<td colspan="2">
<?php 

//managing serial code
//echo $_SESSION["serial"];
if(!isset($_SESSION["serial"]))
{
$_SESSION["serial"]=1;

}

//showing counter
echo "<hr /> 
<h3>You Completed ". ($_SESSION['completed']!=NULL? $_SESSION['completed']:0)." questions, Remaining ".($TOTAL_ITEM-$_SESSION["completed"]).",<a href='posteval.php'>Try Next Question </a>
</h3>
<hr />";
?>
</td>
</tr>
<tr>
<td colspan="2">
<h3>Instructions</h3>
<ul>
<li>You are given 5 questions from StackOverflow</li>
<li>Each question has some answers with code examples</li>
<li>Please review the answers and provide your ratings</li>
<li>For easier evaluation, we provide a list of parameters that you can consider</li>
<li>Also please provide your comments about the answers and code examples</li>
</ul>
</td>
</tr>
<tr>
<td>
<h2 style="color:#FF0000">Please do not refresh this page. Once you are done with reviewing, please save your feednback by pressing <b>Save</b> button at the bottom. To go to next question please click 'Try next question' link at the top and bottom</h2>
</td>
</tr>
<tr>
<td colspan="2">
<div>

<?php 
	
	if(!isset($_POST["submit"]))
	{
	//code for login
	//$host="localhost";
	//$database="sostudy"; //mor543
	//$user="root";
	//$password="";
	include("creds.php");
	$conn=mysql_connect($host,$user,$password);
	$my_question=0;
	if($conn)
	{
	
		mysql_select_db($database,$conn);
		//code for showing the question
	    $select_question="SELECT * from QuestionPost where QSerial=".$_SESSION['serial'];// ORDER BY RAND()LIMIT 1
		$question=mysql_query($select_question,$conn);
		if($question)
		{
			//print_r($result);
			echo "<table border='0' cellpadding='2' cellspacing='2' align='left' width='1000px'>";
			echo "<tr class='hrow'><th colspan='2'>Question</th></tr>";
			while($row=mysql_fetch_row($question))
			{
				$questionID=$row[0];
				//getting the question
				$my_question=$questionID;
				$title=$row[1];
				$description=$row[2];
				echo "<tr><td colspan='2'><h2>$title</h2></td></tr>";
				echo "<tr><td colspan='2'><div>".process_the_code($description)."<div></td></tr>";
				break;
			}
		}
		else
		{
			echo "Failed to retrieve any question";
		}
	}else
	{
		die("Can not connect to the database");
	}
	
	
	
	    
	
	    $conn2=mysql_connect($host,$user,$password);
		if($conn2)
		{
		//code for creating an account
		mysql_select_db($database,$conn2);
		
		$select="SELECT AnswerID, Description FROM AnswerPost where QuestionID=".$my_question;
		$result=mysql_query($select,$conn2);
		if($result)
		{
			$count=0;
			while($row=mysql_fetch_row($result))
			{
				//storing value in session
				$count++;
				$_SESSION["hf".$count]=$row[0];
				
				echo "<tr class='row'><td colspan='2'><b>Answer #".$count."</b></td></tr>";
				//echo "<tr class='inforow'><td colspan='2'>You can check the original post <a target='_blank' href='http://www.stackoverflow.com/questions/$row[1]'>here</a> to review relevant answers, but <span style='color:red'>please do not consider the evaluation of StackOverflow</span>. We are expecting your opinion which is important to us.</td></tr>";
				echo "<tr><td colspan='2'><div>".process_the_code($row[1])."</div></code>
				</td></tr>
				<tr><td colspan='2'>
				<table  border='0' cellpadding='1' cellspacing='1'>
				<tr><td colspan='2'>".provide_evaluation_parameters($row[0],$count)."</td></tr>
				<tr>
				<td valign='top' width='200px'>
				<b style='color:red'>Answer Quality Rating (required):</b></td>
				<td valign='top'>".process_rating($row[0])."</td>
				</tr></table><p>&nbsp;</p>
				</td></tr>";
				
			}
			
			echo "</table>";
		}
		else
		{
			echo "<h3>Oops! Failed to assign question.</h3>";
			echo mysql_error($conn2);
		}
	}
	
	}



    function make_other_option_text($ansid)
	{
		$str="<textarea cols='50'  rows='5' id='feed$ansid' name='feed$ansid'>If you have selected 'Other' please enter your comments here</textarea>";
		return $str;
	}
	

	function provide_evaluation_parameters($ansid,$anscount)
	{
		$params=array("Valid & Accurate","Simple & Explanatory","Efficient & Bug free","Technically sound","Known library usage","Concise solution","Contains irrelevant information","Naive answer","State of art solution","Other");
		
		$str="<h4 class='row'style='padding:5px'>Evaluate Answer $anscount. Please select one or more options which you seem appropriate.</h4><hr>";
		$str.="<p>For examle, please check if the solution provided is valid or accurate for the asked question. Is it simple and easy for understanding? Does the code provide a bug free and efficient solution? Also check the technical merit of the solution that means, is the solution technically enriched? Does it use known and poplular API library? Does the solution contain irrelevant information? Is the provided answer is too naive or of low quality? Does it use advanced API or depricated ones?</p>";
		
		$count=0;
		foreach($params as $param)
		{
			
			$str.="<input type='checkbox' id='chk$ansid-$count' name='chk$ansid-$count' value='$count'>$param"." &nbsp;";
			$count++;
		}
		$str=$str."<br>".make_other_option_text($ansid)."<hr>";
		return $str;
	}
	
	
	
	
	function process_rating($ansid)
	{
		$temp="<select name='rate$ansid' id='rate$ansid'>";
		for($i=0;$i<=5;$i++)
		{
			if($i==0)
			$temp.="<option value='$i'>Unknown</option>";
			if($i==1)
			$temp.="<option value='$i'>Very Good</option>";
			if($i==2)
			$temp.="<option value='$i'>Good</option>";
			if($i==3)
			$temp.="<option value='$i'>Moderate</option>";
			if($i==4)
			$temp.="<option value='$i'>Bad</option>";
			if($i==5)
			$temp.="<option value='$i'>Very Bad</option>";
		}
		$temp.="</select>";
		return $temp;
	}
	
	
	function process_the_code($source_code)
	{
		//code for processing source code
		$semi= array("; ");
		$left=array("{");
		$right=array("}");
		$cmend=array("*/");
		$replace="<br>";
		$str=str_replace($semi,";".$replace,$source_code);
		$str=str_replace($left,"{".$replace,$str);
		$str=str_replace($right,"}".$replace,$str);
		$str=str_replace($cmend,"*/".$replace,$str);
		
		//check for code tag and replacement
		$str=str_replace("<code>","<code class='prettyprint'>",$str);
		return $str;
	}
?>
</div>

</td>
</tr>
<tr>
<td colspan="2">
<input type="submit"  name="submit"  style="font-weight:bold" id="submit" value="Save my ratings and feedback" />
</td>
</tr>
<tr>
<td colspan="2">
<?php 
	if(isset($_POST["submit"]))
	{
		//code for saving the user feedback
		$saved=0;
		for($t=1;$t<=5;$t++)
		{
			
			try
			{
			$ansID=$_SESSION["hf".$t];
			$rate=$_POST['rate'.$ansID];
			$feedback=addslashes($_POST['feed'.$ansID]);
			$metrics=prepare_metrics($ansID);
			//now save it to the database
			//$host="localhost";
			//$database="sostudy"; //mor543
			//$user="root";
			//$password="";
			include("creds.php");
			//$userID=$_SESSION["userid"];
			$conn=mysql_connect($host,$user,$password);
			
			if($conn)
			{
				mysql_select_db($database,$conn);
				$insert="INSERT into PostEvaluation(AnswerID, Rating, CustFeedback,Metrics) VALUES($ansID,$rate,'$feedback','$metrics')";
				$result=mysql_query($insert,$conn);
				if($result)
				{
					$saved++;
				}
				
			}
			}catch(Exception $e){}
		}//end of loop
		
		//initialize the counter
		
		if(!isset($_SESSION["completed"]))
		$_SESSION["completed"]=0;
		
		if($saved>0)
		{
			
			try
			{
			
			if($_SESSION["completed"]<$TOTAL_ITEM)
			{
		        
				
				
				if($_SESSION["serial"]<=$TOTAL_ITEM)
				$_SESSION["serial"]=$_SESSION["serial"]+1;
				
				$_SESSION["completed"]=$_SESSION["completed"]+1;
				if($_SESSION["completed"]==$TOTAL_ITEM)
				{
					echo "<h2 style='color:green'>You successfully completed the  survey. We appreciate your evaluation. Thanks for your time</h2>";
					session_destroy();
				}else if($_SESSION["completed"]<$TOTAL_ITEM) 
				{
					echo "<h2 style='color:green'>Your rankings and feedback saved successfully. Please click the <a href='posteval.php'>Try next question</a> link</h2>";
				}
					
			}
			
			}catch(Exception $exc){
				//$_SESSION["completed"]=1;
			}
			
			
			$to =$_SESSION["email"];
 			$subject = "Thank you for your participation in StackOverflow User study!";
 			$body = "Dear User, \nWe really appreciate your effort and opinions. Thanks again.\n\n Masud";
 			mail($to, $subject, $body);
		}else
		{
			echo "Failed to save information ".mysql_error();
		}
		
		
	}
	
	function prepare_metrics($ansID)
	{
	    
		$id="chk".$ansID;
		for($i=0;$i<10;$i++)
		{
			$chikid=$id."-".$i;
			if(isset($_POST[$chikid]))
			{
				$temp=$_POST[$chikid];
				$metrics.=",".$temp;
			}
			
		}
		
		
		return $metrics;	
	}
	
	
	
?>
</td>
</tr>
<tr>
<td colspan="2">
<?php 
echo "<hr /> 
<h3>You Completed ". ($_SESSION['completed']!=NULL? $_SESSION['completed']:0)." questions, Remaining ".($TOTAL_ITEM-$_SESSION["completed"]).",<a href='posteval.php'>Try Next Question </a>
</h3>
<hr />";



?>
</td>
</tr>
</table>
</form>
</td>
</tr>
<tr>
<td>
<?php include("footer.php");?>
</td>
</tr>
</table>
</body>
</html>
