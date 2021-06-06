var N = 2000;
var EXPECTED = 17393;

function Natural() {
    x = 2;
    return {
        'next': function () {
            return x++;
        }
    };
}

function Filter(number, filter) {
    var self = this;
    this.number = number;
    this.filter = filter;
    this.accept = function (n) {
        var filter = self;
        for (; ;) {
            if (n % filter.number === 0) {
                return false;
            }
            filter = filter.filter;
            if (filter === null) {
                break;
            }
        }
        return true;
    };
    return this;
}

function Primes(natural) {
    var self = this;
    this.natural = natural;
    this.filter = null;

    this.next = function () {
        for (; ;) {
            var n = self.natural.next();
            if (self.filter === null || self.filter.accept(n)) {
                self.filter = new Filter(n, self.filter);
                return n;
            }
        }
    };
}

function primesMain() {
    var primes = new Primes(Natural());
    var primArray = [];
    for (var i = 0; i <= N; i++) {
        primArray.push(primes.next());
    }
    if (primArray[N] !== EXPECTED) {
        throw new Error('wrong prime found: ' + primArray[N]);
    }
}

console.log("---------------------------------------------------primesMain 开始");
primesMain();
console.log("---------------------------------------------------primesMain 1");
primesMain();
console.log("---------------------------------------------------primesMain 2");
primesMain();
console.log("---------------------------------------------------primesMain 结束");

var test = function (a, b) {
    var sum = 0;
    for (var i = 0; i < a; i++) {
        sum += i;
        for (var j = 0; j < b; j++) {
            sum += j;
        }
    }
    return sum;
};
console.log("---------------------------------------------------test 开始");
console.log("---------------------------------------------------test 1 -> ", test(10000, 10000));
console.log("---------------------------------------------------test 2 -> ", test(10000, 10000));
console.log("---------------------------------------------------test 3 -> ", test(10000, 10000));
console.log("---------------------------------------------------test 4-> ", test(10000, 10000));
console.log("---------------------------------------------------test 结束 -> ", test(10000, 10000));

var test2 = function () {
    function factorial(n) {
        return n === 1 ? n : n * factorial(--n);
    }

    var i = 0;
    while (i++ < 1e6) {
        factorial(10);
    }
    return i;
};
console.log("---------------------------------------------------test2 开始");
console.log("---------------------------------------------------test2 1 -> ", test2());
console.log("---------------------------------------------------test2 2 -> ", test2());
console.log("---------------------------------------------------test2 3 -> ", test2());
console.log("---------------------------------------------------test2 4 -> ", test2());
console.log("---------------------------------------------------test2 结束 -> ", test2());
