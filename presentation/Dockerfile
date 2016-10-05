FROM williamyeh/scala

MAINTAINER frantisek.kocun@gmail.com

COPY counter /
COPY Ahoj.scala /
RUN scalac Ahoj.scala
COPY *.scala /

CMD scala Ahoj
