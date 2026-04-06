package br.com.animator.window;

public enum WindowScale {
    X1(1), X2(2), X3(3), X4(4);

    private final int factor;

    WindowScale(int factor) {
        this.factor = factor;
    }

    public int getFactor() {
        return factor;
    }
}
