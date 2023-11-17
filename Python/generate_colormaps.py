import os
import shutil
from typing import Tuple

from mcresources import gradients

SRC = '../src/main/resources/assets/afc/textures/colormap/'


def main():
    make('foliage_green.png', (0, 0, '#1D6233'), (255, 0, '57776D'), (0, 255, '#8EA825'), (255, 255, '#9C8733'))
    # make('foliage_white.png', (0, 0, '#1D6233'), (255, 0, '57776D'), (0, 255, '#8EA825'), (255, 255, '#9C8733'))
    make('foliage_yellow.png', (0, 0, '#fbf236'), (255, 0, '#b1a145'))
    make('foliage_orange.png', (0, 0, '#1D6233'), (255, 0, '57776D'), (0, 255, '#8EA825'), (255, 255, '#9C8733'))
    make('foliage_red.png', (0, 0, '#1D6233'), (255, 0, '57776D'), (0, 255, '#8EA825'), (255, 255, '#9C8733'))

    print('Done')


def make(image: str, *points: Tuple[int, int, str]):
    gradients.create(os.path.join(SRC, image), 256, 256, *points)


def copy(src: str, dest: str):
    shutil.copy(os.path.join(SRC, src), os.path.join(SRC, dest))


if __name__ == '__main__':
    main()
