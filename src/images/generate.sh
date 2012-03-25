#!/bin/bash
#
# Generates the PNG files from the SVG

OUTPUTDIR=../main/resources/de/ailis/xadrian/images

convert -density $[256*72/16] -geometry 256x256 \
  -background none \
  xadrian.svg $OUTPUTDIR/xadrian-256.png

for SIZE in 64 48 32 24 16
do
    convert \
      $OUTPUTDIR/xadrian-256.png \
      -scale ${SIZE}x${SIZE} \
      $OUTPUTDIR/xadrian-${SIZE}.png
done

icotool -c -o ../main/icons/Xadrian.ico $OUTPUTDIR/xadrian-*.png
png2icns ../main/assembly/macosx/xadrian.icns $OUTPUTDIR/xadrian-256.png

#for SIZE in 256 64 48 32 24 16
#do
#    convert -density $[$SIZE*72/16] -geometry ${SIZE}x${SIZE} \
#      -background none -colors 256 \
#      xadrian-noshadow.svg png8:xadrian-${SIZE}-256.png  
#
#    convert -density $[$SIZE*72/16] -geometry ${SIZE}x${SIZE} \
#      -background none -colors 16 \
#      xadrian-simple.svg png8:xadrian-${SIZE}-16.png  
#done
