<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>Info</title>
    <link rel="stylesheet" type="text/css" media="screen" href="factory.css" />    
  </head>
  <body>
    [#if factories?size == 0]
      <p>
        [@message key="factoryInfo.noSelection" /]
      </p>
    [#else]
      [#list factories as factory]
        <h1>${factory.name}</h1>

        <strong>[@message key="factoryInfo.race" /]:</strong> ${factory.race.name}<br />
        <strong>[@message key="factoryInfo.price" /]:</strong> ${factory.price} Cr<br />
        <strong>[@message key="factoryInfo.volume" /]:</strong> ${factory.volume}<br />
        <strong>[@message key="factoryInfo.size" /]:</strong> ${factory.size}<br />
        <strong>[@message key="factoryInfo.cycle" /][#if factory.mine] ([@message key="yield" /] ${factory.defaultYield})[/#if]:</strong> ${factory.defaultCycleAsString}<br />
        <br />
        
        <strong>[@message key="factoryInfo.product" /][#if factory.mine] ([@message key="yield" /] ${factory.defaultYield})[/#if]:</strong><br />
        ${factory.defaultProduct.quantity} ${factory.defaultProduct.ware.name}<br />
        <br />
        
        <strong>[@message key="factoryInfo.resources" /][#if factory.mine] ([@message key="yield" /] ${factory.defaultYield})[/#if]:</strong><br />
        [#list factory.defaultResources as resource]
          ${resource.quantity} ${resource.ware.name}<br />
        [/#list]
        <br />
        
        <strong>[@message key="factoryInfo.storage" /]:</strong><br />
        [#list factory.capacities as capacity]
          ${capacity.quantity} ${capacity.ware.name}<br />
        [/#list]
        <br />
        
        <strong>[@message key="factoryInfo.manufacturers" /]:</strong><br />
        [#list factory.manufacturers as manufacturer]
          ${manufacturer.name}<br />
        [/#list]
        <br />
        
        [#if factory_has_next]        
          <hr />
          <br />
        [/#if]
        
      [/#list]
    [/#if]
  </body>
</html>