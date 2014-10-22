ps -ef | grep java | grep yml | awk '{print($2)}' | xargs kill

