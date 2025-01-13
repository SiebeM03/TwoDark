#type vertex
#version 330
layout (location=0) in vec2 aPos;
layout (location=1) in vec4 aColor;
layout (location=2) in float aTexID;
layout (location=3) in vec2 aTexCoords;

uniform mat4 uProjection;
uniform mat4 uView;

out vec4 fColor;
out float fTexID;
out vec2 fTexCoords;

void main()
{
    fColor = aColor;
    fTexID = aTexID;
    fTexCoords = aTexCoords;

    gl_Position = uProjection * vec4(aPos, 0.0, 1.0);
}

#type fragment
#version 330

in vec4 fColor;
in float fTexID;
in vec2 fTexCoords;

uniform sampler2D uTextures[8];

out vec4 color;

void main()
{
    if (fTexID > 0) {
        int id = int(fTexID);
        color = fColor * texture(uTextures[id], fTexCoords);
    } else {
        color = fColor;
    }
}
