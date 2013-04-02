package nl.esciencecenter.asterisk.visual;

import javax.media.opengl.GL3;

import nl.esciencecenter.asterisk.Sphere;
import nl.esciencecenter.esight.exceptions.UninitializedException;
import nl.esciencecenter.esight.math.MatF4;
import nl.esciencecenter.esight.math.MatrixFMath;
import nl.esciencecenter.esight.math.VecF3;
import nl.esciencecenter.esight.math.VecF4;
import nl.esciencecenter.esight.models.Model;
import nl.esciencecenter.esight.shaders.ShaderProgram;

public class SphereModel {
    private final Model baseModel;
    private final Sphere glueSphere;

    private VecF3 coords;
    private VecF4 color;
    private float radius;

    private boolean initialized;

    public SphereModel(Model baseModel, Sphere glueSphere) {
        this.baseModel = baseModel;
        this.glueSphere = glueSphere;

        initialized = false;
    }

    public void init() {
        if (!initialized) {
            float[] rawCoords = glueSphere.getCoordinates();
            float[] rawColor = glueSphere.getColor();

            coords = new VecF3(rawCoords[0], rawCoords[1], rawCoords[2]);
            color = new VecF4(rawColor[0], rawColor[1], rawColor[2],
                    rawColor[3]);
            radius = glueSphere.getRadius();

            initialized = true;
        }
    }

    public void draw(GL3 gl, ShaderProgram program, MatF4 MVMatrix) {
        if (!initialized) {
            init();
        }

        program.setUniformMatrix("ScaleMatrix", MatrixFMath.scale(radius));

        MatF4 newM = MVMatrix.mul(MatrixFMath.translate(coords));
        program.setUniformMatrix("MVMatrix", newM);

        program.setUniformVector("Color", color);

        try {
            program.use(gl);
        } catch (UninitializedException e) {
            e.printStackTrace();
        }
        baseModel.draw(gl, program);
    }

    public VecF4 getColor() {
        if (!initialized) {
            init();
        }
        return color;
    }

    public float getRadius() {
        if (!initialized) {
            init();
        }

        return radius;
    }

    public VecF3 getLocation() {
        if (!initialized) {
            init();
        }
        return coords;
    }
}
