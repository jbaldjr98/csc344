rectangle([Left,Bottom,W,H]).
%checks if point is in the rectangle
inrect(rectangle([L,B,W,H]),X,Y):-
    % X is higher then leftmost point
    X>= L,
    % X is less than the rightmost point
    X < W+L+1,
    % Y is above the bottom
    Y>=B,
    % Y is below the top
    Y < B+H+1.
 % Move the drone right
 move([X,Y],[X1,Y]):-
    X1 is X+1.
% move up
 move([X,Y],[X,Y1]):-
    Y1 is Y+1.
% move down
 move([X,Y],[X,Y0]):-
    Y0 is Y-1.
%not negative
notNeg(X,Y):-
    X > -1,
    Y > -1.
safe([X,Y],[A|B]):-
    nth0(0,A,W1),
    nth0(1,A,H1),
    nth0(2,A,X1),
    nth0(3,A,Y1),
    not(inrect(rectangle([X1,Y1,W1,H1]),X,Y)),
    safe([X,Y],B).
safe([_,_],[]).
safe([],[]).
visited([_,_],[]).
visited([X,Y],[A|B]):-
	[X,Y] \= A,
    visited([X,Y],B).
inCave([X,Y],[X1,Y1]):-
     X < X1+1,
     Y < Y1+1,
     notNeg(X,Y). 
go(_,[X,_],X0,_,R1,AGAIN):-
    X==X0,
    write(R1).
% go through cave
go(CAVE,[W,H],X0,Y0,ROUTE,AGAIN):-
    notNeg(X0,Y0),
    move([X0,Y0],[X1,Y1]),
    notNeg(X1,Y1),
    safe([X1,Y1],CAVE),
    inCave([X1,Y1],[W,H]),
    visited([X1,Y1],ROUTE),
    R1 = [[X1,Y1]|ROUTE],
    go(CAVE,[W,H],X1,Y1,R1,AGAIN).
	
    