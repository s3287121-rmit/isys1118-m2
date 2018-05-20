================================================================================
=========================   Thursday 4:30 -- Group 1   =========================
=========================         Milestone 2          =========================
=========================                              =========================
=========================  s328721 - Luke Larobina     =========================
=========================  s3599805 - David Kilgower   =========================
=========================  s3586806 - Ruochen Ying     =========================
=========================  s3599490 - Jianwei Lai      =========================
================================================================================

>>>>    1.0 FUNCTIONAL REQUIREMENTS

These are the functional requirements that have been done so far.

>>  1.1 Login [X]

>>  1.2 View budget [DONE]
Each course will show a preset maximum budget and a calculated cost of
of employing casuals.

>>  1.3 Calculate rate [DONE]
Costs of hiring casuals is calculated correctly and shown.

>>  1.4 Add causal to activity [X]

>>  1.5 Add activity [DONE]
Users can add a new activity from the Course page.

>>  1.6 Edit activity [DONE]
Users can update an activity by choosing to edit them (from the Activity page).

>>  1.7 View all activities [DONE]
After selecting a Course, users can choose to view an Activity by selecting it.

>>  1.8 Send request for course approval [DONE]
Course coordinators can select a course for approval from the Course page.

>>  1.9 Approve / request course [DONE]
Options are given to users to approve or reject a Course. Done from the Course
page.

>>  1.10 Update database with changes to information [DONE]
Changes to activities and course approval status are updated each time a user
changes them.

>>  1.11 Check availability [X]

>>  1.12 View causal information [X]


>>>>    2.0 RUNNING APPLICATION

There are two methods for running the application: by using Eclipse or Tomcat.
These methods will be refered to as "Running Source" and "Running Release"
respectively.

>>  2.1 Running Source

To run the app using the source code, you will need to install the GWT plugin
for Eclipse (http://www.gwtproject.org/download.html).

Once installed, the source project can be copied from the
"application > source" directory and imported as a Java project in Eclipse.

After it is set up, you can choose to run the app as
"GWT Development Dode with Jetty".

The console will then provide you with a link that opens in your browser.

>>  2.2 Running Release

To run the release version of the app, you will need to install Apache Tomcat
7.0 (https://tomcat.apache.org/download-70.cgi).

Once installed, find the "SEF_Assignment.war" file in the "release" file and
copy that into the "webapps" directory where Tomcat was installed.

Start Tomcat, and it should create a new directory called "SEF_Assignment" in
the "webapps" directory. When this is done, you will be able to access the
app on any browser through the following link:

localhost:[port]/SEF_Assignment/
OR
127.0.0.1:[port]/SEF_Assignment/

Where [port] is usually 8080, but depending on how Tomcat was set up, this may
be different.

IMPORTANT NOTE: Please check >> 3.1 if the localhost link does not work.

>>>>    3.0 TROUBLESHOOTING AND KNOWN BUGS

>>  3.1 "localhost" Issues

There are some problems when using the "localhost:[port]/SEF_Assignment/" URL.
I'm not sure what's causing this problem right now, but it causes the app not
to load. The solution right now is to replace "localhost" with your local IP
(usually 127.0.0.1). So if localhost is not working, please try that.

>>  3.2 It still doesn't work! Help!

Yeah, I know, it's buggy as hell. Please try a different browser. It seems to
work fine on Firefox when not using localhost, and it works fine on Edge with
either localhost or a local IP. Chrome doesn't seem to respond at all. But it
might be different for you because every time I recompile it gives different
results.

>>  3.2 Database Resetting

Due to problems with file permissions, a function for resetting the database
had to be disabled. If you wish to reset the database, please go to
"SEF_Assignment > WEB-INF > db". In that folder is a "backup" directory. Just
copy the files from there into the parent "db" directory.

>>  3.3 .war file not copying

If, for some reason, the .war file does not automatically extract, you can do
it manually. Just make sure that you extract all of the .war file's contents
into a directory called "SEF_Assignment". This directory should be in the
"[path-to-tomcat] > webapps" directory.

>>>>    4.0 OTHER QUESTIONS

>>  4.1 Where are your comments?

Ummm...

>>  4.2 Why does this look like spaghetti code?

Well...

>>  4.3 Seriously, how am I supposed to make sense of this?

If you try squinting your eyes a little...

>>  4.4 Did you rush this?

Err...

>>  4.5 This class from one of your diagrams doesn't match your implementation!

Yeah...

>>  4.6 Why are there so many bugs?

Because it's summer...?

>>  4.7 And why is this database not an actual database?

Hmmm...