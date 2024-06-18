const spawn = require('child_process').spawn;

const result = spawn('python', ['scraping.py']);

result.stdout.on('data', function(data) {
    console.log(data.toString());
});

result.stderr.on('data', function(data) {
    console.log('err : ' + data.toString());
});