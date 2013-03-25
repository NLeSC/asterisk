package nl.esciencecenter.asterisk.visual;

import java.nio.FloatBuffer;

import javax.media.opengl.GL3;

import nl.esciencecenter.esight.datastructures.GLSLAttrib;
import nl.esciencecenter.esight.datastructures.VBO;
import nl.esciencecenter.esight.shaders.ShaderProgram;

public class PointCloud {
    private VBO vbo;
    private boolean initialized;

    private final int numParticles;
    private final FloatBuffer coordinates;
    private final FloatBuffer colors;

    public PointCloud(int numParticles, FloatBuffer coordinates,
            FloatBuffer colors) {
        this.numParticles = numParticles;
        this.coordinates = coordinates;
        this.colors = colors;
    }

    public void init(GL3 gl) {
        if (!initialized) {
            coordinates.rewind();
            colors.rewind();

            GLSLAttrib vAttrib = new GLSLAttrib(coordinates, "MCvertex",
                    GLSLAttrib.SIZE_FLOAT, 3);

            GLSLAttrib cAttrib = new GLSLAttrib(colors, "MCcolor",
                    GLSLAttrib.SIZE_FLOAT, 4);

            vbo = new VBO(gl, vAttrib, cAttrib);
            initialized = true;
        }
    }

    public void draw(GL3 gl, ShaderProgram program) {
        vbo.bind(gl);

        program.linkAttribs(gl, vbo.getAttribs());

        gl.glDrawArrays(GL3.GL_POINTS, 0, numParticles);
    }

    public void dispose(GL3 gl) {
        vbo.delete(gl);
    }

    public int getSize() {
        return numParticles;
    }
}
