
The assignment
N students approach homework defense (each defense process is performed in a separate thread). The defense is held by a professor and an assistant. Each student defends the task at his own pace (a random value in the range of 0.5 <= x <= 1 second). The assistant is able to examine only one student while the professor is able to monitor two defenses simultaneously. Each student comes to defense for a period of time (a random value in the range of 0 < x <= 1 second from the start of the defense term). Defense term lasts 5 seconds. The defense that has been started must be interrupted by the moment when the term of defense is completed.

The professor will not accept to examine only one student, instead, he'll wait two students who are ready to defend and then start exammining both, simultaneously, until both are finished.

Each student, upon completion of his defense, must refresh the sum of the grades of all students by adding his own. These grades will be divided by the number of students that finnished defending, and this result should be printed in the console at the end of the defense term.

The professor can examine exactly two students while the assistant can examine exactly one. It must not happen that the same student defends the homework twice. It should not happen that a student defends homework after the end of the defense term, even if it began before the defense term end. It should not happen that the professor and the assistant examine the same student.
