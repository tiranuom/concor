function toTime(value) {
    if (value / 1000 < 1) return value + "ns";
    if (value / 1000000 < 1) return value / 1000 + "ms";
    return value / 1000000 + "s";
}

it('should give correct time conversions', function () {
    expect(toTime(100)).toBe('100ns')
    expect(toTime(1000)).toBe('1ms')
    expect(toTime(1000000)).toBe('1s')
    expect(toTime(1100)).toBe('1.1ms')
});