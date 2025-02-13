#type vertex
#version 330
layout (location=0) in vec2 aPos;
layout (location=1) in vec4 aColor;
layout (location=2) in vec2 aTexCoords;

uniform mat4 uProjection;
uniform mat4 uView;

out vec4 fColor;
out vec2 fTexCoords;

void main()
{
    fColor = aColor;
    fTexCoords = aTexCoords;

    gl_Position = uProjection * vec4(aPos, 0.0, 1.0);
}

#type fragment
#version 330

in vec4 fColor;
in vec2 fTexCoords;

uniform sampler2D fontTexture;

out vec4 color;

void main()
{
    color = vec4(fColor.xyz, 0.8);// Comment line below to render bounding boxes of each char instead of the actual char
    color = fColor * texture(fontTexture, fTexCoords);
}