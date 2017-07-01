@set out_src=java
set language=java
set src=proto
@set import=-I.\%src%
@echo off
FOR   %%f   IN   (%src%/*.proto)   DO   (   
  echo %%f
  protoc2.6\protoc %import%  --%language%_out=%out_src% %src%\%%f 
)
pause