FROM  openjdk:8u171-jdk-alpine3.7
MAINTAINER beatrizcampos@id.uff.br
RUN mkdir /app
RUN mkdir /app/log
RUN mkdir /app/bin
RUN mkdir /app/tmp
VOLUME /app/tmp
VOLUME /app/log
COPY . /
WORKDIR /app/bin/
RUN chmod u+rx run.sh && date >> build-timestamp
CMD ["/app/bin/run.sh"]