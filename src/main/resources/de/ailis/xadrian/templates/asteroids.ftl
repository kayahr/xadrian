<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>Info</title>
    <link rel="stylesheet" type="text/css" media="screen" href="asteroids.css" />    
  </head>
  <body>
    [#if !sector??]
      <p>
        [@message key="asteroidsInfo.noSector" /]
      </p>
    [#else]
      <h1>${sector.name}</h1>
      
      [#if sector.asteroids?size == 0]
        <p>
          [@message key="asteroidsInfo.noAsteroids" /]
        </p>
      [#else]
            
        [#assign asteroids=sector.siliconAsteroids]
        [#if asteroids?size > 0]
        <h2>[@message key="asteroidsInfo.siliconAsteroids" /]:</h2>
        <p class="yields">
          [#list asteroids as asteroid]
            ${asteroid.yield}[#if asteroid_has_next], [/#if]
          [/#list]        
        </p>
        [/#if]
        
        [#assign asteroids=sector.oreAsteroids]
        [#if asteroids?size > 0]
        <h2>[@message key="asteroidsInfo.oreAsteroids" /]:</h2>
        <p class="yields">
          [#list asteroids as asteroid]
            ${asteroid.yield}[#if asteroid_has_next], [/#if]
          [/#list]        
        </p>
        [/#if]
       
        [#assign asteroids=sector.iceAsteroids]
        [#if asteroids?size > 0]
        <h2>[@message key="asteroidsInfo.iceAsteroids" /]:</h2>
        <p class="yields">
          [#list asteroids as asteroid]
            ${asteroid.yield}[#if asteroid_has_next], [/#if]
          [/#list]        
        </p>
        [/#if]
      
        [#assign asteroids=sector.nividiumAsteroids]
        [#if asteroids?size > 0]
        <h2>[@message key="asteroidsInfo.nividiumAsteroids" /]:</h2>
        <p class="yields">
          [#list asteroids as asteroid]
            ${asteroid.yield}[#if asteroid_has_next], [/#if]
          [/#list]        
        </p>
        [/#if]
        
      [/#if]
      
    [/#if]
  </body>
</html>
