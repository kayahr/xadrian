<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>${complex.name}</title>
    <link rel="stylesheet" type="text/css" href="complex.css" />    
  </head>
  [#if print]
    [#assign class="print"]
  [#else]
    [#assign class="screen"]
  [/#if]  
  <body class="${class}">
    <h1>${complex.name}</h1>
    <h2>
      <a href="file://toggleShowingComplexSetup">
        [#if !print]
          <img src="../images/[#if complex.showingComplexSetup]expanded[#else]collapsed[/#if].png" border="0" width="16" height="14" />
        [/#if]
        [@message key="complex.complexSetup" /]
      </a>
    </h2>
    [#if complex.showingComplexSetup || print]
      <p class="indent">
        [@message key="complex.sector" /]:
        [#if !print]<a href="file://changeSector">[/#if][#if complex.sector??]${complex.sector}[#else][@message key="complex.noSector" /][/#if][#if !print]</a>[/#if]
      
        <br />
      
        [@message key="complex.suns" /]:
        [#if !print && !complex.sector??]<a href="file://changeSuns">[/#if]${complex.suns}[#if !print && !complex.sector??]</a>[/#if]
      </p>
      [#if complex.factories?size == 0]
        <p class="indent">[@message key="complex.noFactories" /]</p>
      [#else]
        <table class="indent">
          <tr>
            <th class="factory">[@message key="complex.factory" /]</th>
            <th class="race">[@message key="complex.race" /]</th>
            <th class="yield">[@message key="complex.yield" /]</th>
            <th class="quantity">[@message key="complex.quantity" /]</th>
            <th class="singlePrice">[@message key="complex.unitPrice" /]</th>
            <th class="price">[@message key="complex.price" /]</th>
            [#if !print]<td class="buttons"></td>[/#if]
          </tr>
          <tr>
            <td colspan="6" class="sep"<img src="../images/blank.png" width="1" height="1" /></td>
            [#if !print]<td class="sep"<img src="../images/blank.png" width="1" height="1" /></td>[/#if]
          </tr>
          [#list complex.factories as complexFactory]
            [#if complexFactory_index %2 == 0]
              [#assign class="even" /]
            [#else]
              [#assign class="odd" /]
            [/#if]
            [#assign factory=complexFactory.factory]
            <tr class="${class}">
              <td class="factory">
                [#if complexFactory.disabled]
                  <strike>${factory.name}</strike>
                [#else]
                  ${factory.name}
                [/#if]
                [#if config.showFactoryResources]
                  <div class="factory-resources">
                    [#if complexFactory.disabled]
                      <strike>[#list factory.resources as resource]${resource.ware.name}[#if resource_has_next], [/#if][/#list]</strike>
                    [#else]
                      [#list factory.resources as resource]${resource.ware.name}[#if resource_has_next], [/#if][/#list]
                    [/#if]
                  </div>
                [/#if]
              </td>
              <td class="race">${factory.race.name}</td>
              <td class="yield">
                [#if complexFactory.factory.mine]
                  [#if !print]<a href="file://changeYield/${complexFactory_index}">[/#if][#if !complexFactory.homogenousYield]~[/#if] ${complexFactory.yield}[#if !print]</a>[/#if]
                [/#if]
              </td>
              <td class="quantity">
                [#if !print]
                  <table class="layout"><tr>
                    [#if complexFactory.factory.mine]
                      <td>${complexFactory.quantity}&nbsp;&nbsp;</td>
                      <td><img src="../images/blank.png" width="12" height="12" /></td>
                      <td><img src="../images/blank.png" width="12" height="12" /></td>
                    [#else]
                      <td><a href="file://changeQuantity/${complexFactory_index}">${complexFactory.quantity}</a>&nbsp;&nbsp;</td>
                      <td><a href="file://increaseQuantity/${complexFactory_index}"><img src="../images/up12.png" border="0" width="12" height="12" /></a></td>
                      <td><a href="file://decreaseQuantity/${complexFactory_index}"><img src="../images/down12.png" border="0" width="12" height="12" /></a></td>
                    [/#if]
                  </tr></table>
                [#else]
                  ${complexFactory.quantity}
                [/#if]
              </td>
              <td class="singlePrice">${factory.price} Cr</td>
              <td class="price">${factory.price * complexFactory.quantity} Cr</td>
              [#if !print]
              <td>
                <table class="layout"><tr>       
                  <td><a href="file://removeFactory/${complexFactory_index}"><img src="../images/close.png" border="0" width="16" height="16" /></a></td>
                  [#if complexFactory.disabled]
                    <td><a href="file://enableFactory/${complexFactory_index}"><img src="../images/start.png" border="0" width="16" height="16" /></a></td>
                  [#else]
                    <td><a href="file://disableFactory/${complexFactory_index}"><img src="../images/pause.png" border="0" width="16" height="16" /></a></td>
                  [/#if]
                </tr></table>
              </td>
              [/#if]
            </tr>
          [/#list]
          [#if complex.autoFactories?size > 0]
            <tr><td colspan="7" class="sep"><img src="../images/blank.png" width="1" height="1" /></td></tr>
            [#list complex.autoFactories as complexFactory]
              [#if (complex.factories?size + complexFactory_index) % 2 == 0]
                [#assign class="autoeven" /]
              [#else]
                [#assign class="autoodd" /]
              [/#if]
              [#assign factory=complexFactory.factory]
              <tr class="${class}">
                <td class="factory">${factory.name}
                  [#if config.showFactoryResources]
                    <div class="factory-resources">
                      [#list factory.resources as resource]${resource.ware.name}[#if resource_has_next], [/#if][/#list]
                    </div>
                  [/#if]
                </td>
                <td class="race">${factory.race.name}</td>
                <td class="yield">
                  [#if complexFactory.factory.mine]
                    ${complexFactory.yield}
                  [/#if]
                </td>
                <td class="quantity">${complexFactory.quantity}</td>
                <td class="singlePrice">${factory.price} Cr</td>
                <td class="price">${factory.price * complexFactory.quantity} Cr</td>
                [#if !print]
                <td>       
                  <a href="file://acceptFactory/${complexFactory_index}"><img src="../images/add.png" border="0" alt="" width="16" height="16" /></a>
                </td>
                [/#if]
              </tr>
            [/#list]
          [/#if]
          [#if complex.kitQuantity > 0]
            [#if (complex.autoFactories?size + complex.factories?size) % 2 == 0]
              [#assign class="kitseven" /]
            [#else]
              [#assign class="kitsodd" /]
            [/#if]
            <tr><td colspan="7" class="sep"><img src="../images/blank.png" width="1" height="1" /></td></tr>
            <tr class="${class}">
              <td colspan="3" class="factory">[@message key="complex.kit" /]</td>
              <td class="quantity">${complex.kitQuantity}</td>
              <td class="singlePrice">${complex.kitPrice} Cr</td>
              <td class="price">${complex.totalKitPrice} Cr</td>
              [#if !print]<td></td>[/#if]
            </tr>
          [/#if]
          <tr>
            <td colspan="6" class="sep"<img src="../images/blank.png" width="1" height="1" /></td>
            [#if !print]<td class="sep"<img src="../images/blank.png" width="1" height="1" /></td>[/#if]
          </tr>
          <tr>
            <th colspan="3">[@message key="complex.total" /]</th>
            <td class="quantity">
              ${complex.totalQuantity}
              [#if complex.kitQuantity > 0]
              (+${complex.kitQuantity})
              [/#if]
            </td>
            <td></td>
            <td class="price">${complex.totalPrice} Cr</td>
            [#if !print]<td></td>[/#if]
          </tr>
        </table>
      [/#if]
      [#if !print]
      <p class="indent">
        <a href="file://addFactory">[@message key="complex.addFactory" /]</a>
      </p>
      [/#if]
    [/#if]
    
    [#if complex.factories?size > 0]
      <h2>
        <a href="file://toggleShowingProductionStats">
          [#if !print]
            <img src="../images/[#if complex.showingProductionStats]expanded[#else]collapsed[/#if].png" border="0" width="16" height="14" />
          [/#if]
          [@message key="complex.productionStats" /]
        </a>
      </h2>
      [#if complex.showingProductionStats || print]
        <table class="indent">
          <tr>
            <th class="ware">[@message key="complex.ware" /]</th>
            <th class="units">[@message key="complex.produced" /]</th>
            <th class="units">[@message key="complex.needed" /]</th>
            <th class="units">[@message key="complex.surplus" /]</th>
            <th class="price">[@message key="complex.buyPrice" /]</th>
            <th class="price">[@message key="complex.sellPrice" /]</th>
            <th class="profit">[@message key="complex.profit" /]</th>
          </tr>
          <tr>
            <td colspan="4" class="sep"<img src="../images/blank.png" width="1" height="1" /></td>
            <td colspan="3" class="sep"<img src="../images/blank.png" width="1" height="1" /></td>
          </tr>
          [#list complex.wares as complexWare]
            [#if complexWare_index %2 == 0]
              [#assign class="even" /]
            [#else]
              [#assign class="odd" /]
            [/#if]
            <tr class="${class}">
              <td class="ware">${complexWare.ware.name}</td>
              <td class="units">${complexWare.produced?round}</td>
              <td class="units">${complexWare.needed?round}</td>
              [#assign surplus = complexWare.produced - complexWare.needed]
              [#if surplus > 0]
                [#assign class="surplus"]
              [#elseif surplus lt 0]
                [#assign class="missing"]
              [#else]
                [#assign class="balanced"]
              [/#if]
              <td class="${class}">${surplus?round}</td>
              <td class="price">
                [#if surplus lt 0]
                  <a href="file://changePrice/${complexWare.ware.id}">[#if complexWare.price gt 0]${complexWare.price} Cr[#else][@message key="complex.noTrade" /][/#if]</a>
                [#else]
                  -
                [/#if]
              </td>          
              <td class="price">
                [#if surplus gt 0]
                  <a href="file://changePrice/${complexWare.ware.id}">[#if complexWare.price gt 0]${complexWare.price} Cr[#else][@message key="complex.noTrade" /][/#if]</a>
                [#else]
                  -
                [/#if]
              </td>          
              <td class="profit">${complexWare.profit?round} Cr</td>
            </tr>
          [/#list]
          <tr>
            <td colspan="4" class="sep"<img src="../images/blank.png" width="1" height="1" /></td>
            <td colspan="3" class="sep"<img src="../images/blank.png" width="1" height="1" /></td>
          </tr>
          <tr>
            <th class="profit" colspan="6">[@message key="complex.total" /]</th>
            <td class="profit">${complex.profit?round} Cr</td>          
          </tr>        
        </table>
      [/#if]
  
      <h2>
        <a href="file://toggleShowingStorageCapacities">
          [#if !print]
            <img src="../images/[#if complex.showingStorageCapacities]expanded[#else]collapsed[/#if].png" border="0" width="16" height="14" />
          [/#if]
          [@message key="complex.storageCapacities" /]
        </a>
      </h2>
      [#if complex.showingStorageCapacities || print]
        <table class="indent">
          <tr>
            <th class="ware">[@message key="complex.ware" /]</th>
            <th class="container">[@message key="complex.container" /]</th>
            <th class="volume">[@message key="complex.wareVolume" /]</th>
            <th class="units">[@message key="complex.storageUnits" /]</th>
            <th class="volume">[@message key="complex.storageVolume" /]</th>
          </tr>
          <tr>
            <td colspan="5" class="sep"<img src="../images/blank.png" width="1" height="1" /></td>
          </tr>
          [#list complex.capacities as capacity]
            [#if capacity_index %2 == 0]
              [#assign class="even" /]
            [#else]
              [#assign class="odd" /]
            [/#if]
            <tr class="${class}">
              <td class="ware">${capacity.ware.name}</td>
              <td class="container">${capacity.ware.container}</td>
              <td class="volume">${capacity.ware.volume}</td>
              <td class="units">${capacity.quantity}</td>
              <td class="volume">${capacity.volume}</td>
            </tr>
          [/#list]
          <tr>
            <td colspan="5" class="sep"<img src="../images/blank.png" width="1" height="1" /></td>
          </tr>
          <tr>
            <th class="ware">[@message key="complex.total" /]</th>
            <td></td>          
            <td></td>          
            <td class="profit">${complex.totalCapacity}</td>
            <td class="volume">${complex.totalStorageVolume}</td>          
          </tr>        
        </table>
      [/#if]
  
      [#include "shoppinglist.ftl"]
    [/#if]
  </body>
</html>