#type vertex
#version 330
layout (location=0) in vec2 aPos;
layout (location=1) in vec4 aColor;
layout (location=2) in float aTexID;
layout (location=3) in vec2 aTexCoords;
layout (location=4) in float aEntityId;

uniform mat4 uProjection;
uniform mat4 uView;

out vec4 fColor;
out float fTexID;
out vec2 fTexCoords;
out float fEntityId;

void main()
{
    fColor = aColor;
    fTexID = aTexID;
    fTexCoords = aTexCoords;
    fEntityId = aEntityId;

    gl_Position = uProjection * uView * vec4(aPos, 0.0, 1.0);
}

#type fragment
#version 330

in vec4 fColor;
in float fTexID;
in vec2 fTexCoords;
in float fEntityId;

uniform sampler2D uTextures[8];

out vec3 color;

void main()
{
    vec4 texColor = vec4(1, 1, 1, 1);
    if (fTexID > 0) {
        int id = int(fTexID);
        texColor = fColor * texture(uTextures[id], fTexCoords);
    }

    if (texColor.a < 0.5) {
        discard;
    }
    color = vec3(fEntityId, fEntityId, fEntityId);
}