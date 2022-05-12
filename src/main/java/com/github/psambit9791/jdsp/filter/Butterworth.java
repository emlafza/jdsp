/*
 * Copyright (c) 2020 Sambit Paul
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.psambit9791.jdsp.filter;

/**
 * <h1>Butterworth Filter</h1>
 * The Butterworth class implements low-pass, high-pass, band-pass and band-stop filter using the Butterworth polynomials.
 * Has the flattest pass-band but a poor roll-off rate.
 * Reference <a href="https://en.wikipedia.org/wiki/Butterworth_filter">article</a> for more information on Buttterworth Filters.
 * <p>
 *
 * @author  Sambit Paul
 * @version 2.0
 */
public class Butterworth implements _IIRFilter {
    private double samplingFreq;

    /**
     * This constructor initialises the prerequisites
     * required to use Butterworth filter.
     * @param Fs Sampling frequency of input signal
     */
    public Butterworth(double Fs) {
        this.samplingFreq = Fs;
    }

    /**
     * This method implements a low pass filter with given parameters, filters the signal and returns it.
     * @param signal Signal to be filtered
     * @param order Order of the filter
     * @param cutoffFreq The cutoff frequency for the filter in Hz
     * @return double[] Filtered signal
     */
    public double[] lowPassFilter(double[] signal, int order, double cutoffFreq) {
        double[] output = new double[signal.length];
        uk.me.berndporr.iirj.Butterworth lp = new uk.me.berndporr.iirj.Butterworth();
        lp.lowPass(order, this.samplingFreq, cutoffFreq);
        for (int i =0; i<output.length; i++) {
            output[i] = lp.filter(signal[i]);
        }
        return output;
    }

    /**
     * This method implements a high pass filter with given parameters, filters the signal and returns it.
     * @param signal Signal to be filtered
     * @param order Order of the filter
     * @param cutoffFreq The cutoff frequency for the filter in Hz
     * @return double[] Filtered signal
     */
    public double[] highPassFilter(double[] signal, int order, double cutoffFreq) {
        double[] output = new double[signal.length];
        uk.me.berndporr.iirj.Butterworth hp = new uk.me.berndporr.iirj.Butterworth();
        hp.highPass(order, this.samplingFreq, cutoffFreq);
        for (int i =0; i<output.length; i++) {
            output[i] = hp.filter(signal[i]);
        }
        return output;
    }

    /**
     * This method implements a band pass filter with given parameters, filters the signal and returns it.
     * @param signal Signal to be filtered
     * @param order Order of the filter
     * @param lowCutoff The lower cutoff frequency for the filter in Hz
     * @param highCutoff The upper cutoff frequency for the filter in Hz
     * @throws java.lang.IllegalArgumentException The lower cutoff frequency is greater than the higher cutoff frequency
     * @return double[] Filtered signal
     */
    public double[] bandPassFilter(double[] signal, int order, double lowCutoff, double highCutoff) throws IllegalArgumentException{
        if (lowCutoff >= highCutoff) {
            throw new IllegalArgumentException("Lower Cutoff Frequency cannot be more than the Higher Cutoff Frequency");
        }
        double centreFreq = (highCutoff + lowCutoff)/2.0;
        double width = Math.abs(highCutoff - lowCutoff);
        double[] output = new double[signal.length];
        uk.me.berndporr.iirj.Butterworth bp = new uk.me.berndporr.iirj.Butterworth();
        bp.bandPass(order, this.samplingFreq, centreFreq, width);
        for (int i=0; i<output.length; i++) {
            output[i] = bp.filter(signal[i]);
        }
        return output;
    }

    /**
     * This method implements a band stop filter with given parameters, filters the signal and returns it.
     * @param signal Signal to be filtered
     * @param order Order of the filter
     * @param lowCutoff The lower cutoff frequency for the filter in Hz
     * @param highCutoff The upper cutoff frequency for the filter in Hz
     * @throws java.lang.IllegalArgumentException The lower cutoff frequency is greater than the higher cutoff frequency
     * @return double[] Filtered signal
     */
    public double[] bandStopFilter(double[] signal, int order, double lowCutoff, double highCutoff) throws IllegalArgumentException{
        if (lowCutoff >= highCutoff) {
            throw new IllegalArgumentException("Lower Cutoff Frequency cannot be more than the Higher Cutoff Frequency");
        }
        double centreFreq = (highCutoff + lowCutoff)/2.0;
        double width = Math.abs(highCutoff - lowCutoff);
        double[] output = new double[signal.length];
        uk.me.berndporr.iirj.Butterworth bs = new uk.me.berndporr.iirj.Butterworth();
        bs.bandStop(order, this.samplingFreq, centreFreq, width);
        for (int i=0; i<output.length; i++) {
            output[i] = bs.filter(signal[i]);
        }
        return output;
    }
}
