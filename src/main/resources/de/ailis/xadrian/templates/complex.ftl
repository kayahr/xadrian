<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>${name}</title>
    <link rel="stylesheet" type="text/css" media="screen" href="common.css" />    
  </head>
  <body>
    <h1>${name}</h1>
    [#if factories?size == 0]
      <p>[@message key="complex.noFactories" /]</p>
    [#else]
      <p>
        [@message key="complex.suns" />:
        <a href="file://changeSuns">${suns}</a> %
      </p>
      <table class="complex">
        <tr>
          <th class="factory">[@message key="complex.factory" /]</th>
          <th class="race">[@message key="complex.race" /]</th>
          <th class="yield">[@message key="complex.yield" /]</th>
          <th class="quantity">[@message key="complex.quantity" /]</th>
          <th class="singlePrice">[@message key="complex.unitPrice" /]</th>
          <th class="price">[@message key="complex.price" /]</th>
          <td class="buttons"></td>
        </tr>
        <tr>
          <td colspan="6"><hr /></td>
          <td><hr /></td>
        </tr>
        [#list factories as complexFactory]
          [#if complexFactory_index %2 == 0]
            [#assign class="even" /]
          [#else]
            [#assign class="odd" /]
          [/#if]
          [#assign factory=complexFactory.factory]
          <tr class="${class}">
            <td class="factory">${factory.name}</td>
            <td class="race">${factory.race.name}</td>
            <td class="yield">
              [#if complexFactory.factory.type == 'MINE']
                <a href="file://changeYield/${complexFactory_index}">${complexFactory.yield}</a>
              [/#if]
            </td>
            <td class="quantity">
              [#if complexFactory.factory.type != 'MINE']
                <a href="file://changeQuantity/${complexFactory_index}">${complexFactory.quantity}</a>
              [#else]
                ${complexFactory.quantity}
              [/#if]
            </td>
            <td class="singlePrice">${factory.price} Cr</td>
            <td class="price">${factory.price * complexFactory.quantity} Cr</td>
            <td>       
              <a href="file://removeFactory/${complexFactory_index}"><img src="../images/close.png" border="0" alt="" /></a>
            </td>
          </tr>
        [/#list]
        [#if autoFactories?size > 0]
          <tr class="sep"><td colspan="7"></td></tr>
          [#list autoFactories as complexFactory]
            [#if (factories?size + complexFactory_index) % 2 == 0]
              [#assign class="autoeven" /]
            [#else]
              [#assign class="autoodd" /]
            [/#if]
            [#assign factory=complexFactory.factory]
            <tr class="${class}">
              <td class="factory">${factory.name}</td>
              <td class="race">${factory.race.name}</td>
              <td class="yield">
                [#if complexFactory.factory.type == 'MINE']
                  ${complexFactory.yield}
                [/#if]
              </td>
              <td class="quantity">
                [#if complexFactory.factory.type != 'MINE']
                  ${complexFactory.quantity}
                [#else]
                  ${complexFactory.quantity}
                [/#if]
              </td>
              <td class="singlePrice">${factory.price} Cr</td>
              <td class="price">${factory.price * complexFactory.quantity} Cr</td>
            <td>       
              <a href="file://acceptFactory/${complexFactory_index}"><img src="../images/add.png" border="0" alt="" /></a>
            </td>
            </tr>
          [/#list]
        [/#if]
        [#if kitQuantity > 0]
          [#if (autoFactories?size + factories?size) % 2 == 0]
            [#assign class="kitseven" /]
          [#else]
            [#assign class="kitsodd" /]
          [/#if]
          <tr class="sep"><td colspan="7"></td></tr>
          <tr class="${class}">
            <td class="factory">[@message key="complex.kit" /]</td>
            <td colspan="2"></td>
            <td class="quantity">${kitQuantity}</td>
            <td class="singlePrice">${kitPrice} Cr</td>
            <td class="price">${totalKitPrice} Cr</td>
          </tr>
        [/#if]
        <tr>
          <td colspan="6"><hr /></td>
          <td><hr /></td>
        </tr>
        <tr>
          <th colspan="3">[@message key="complex.total" /]</th>
          <td class="quantity">
            ${totalQuantity}
            [#if kitQuantity > 0]
            (+${kitQuantity} [@message key="complex.kits" /])
            [/#if]
          </td>
          <td></td>
          <td class="price">${totalPrice} Cr</td>
          <td></td>
        </tr>
      </table>
    [/#if]
    <p>
      <a href="file://addFactory">[@message key="complex.addFactory" /]</a>
    </p>
    [#if factories?size > 0]      
      <h2>Production statistics (per Hour)</h2>
      <table>
        <tr>
          <th class="ware">Ware</th>
          <th class="units">Produced</th>
          <th class="units">Needed</th>
          <th class="units">Surplus</th>
          <th class="price">Buy</th>
          <th class="price">Sell</th>
          <th class="profit">Profit</th>
        </tr>
        <tr>
          <td colspan="4"><hr /></td>
          <td colspan="3"><hr /></td
        </tr>
        [#list wares as complexWare]
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
            [#elseif surplus < 0]
              [#assign class="missing"]
            [#else]
              [#assign class="balanced"]
            [/#if]
            <td class="${class}">${surplus?round}</td>
            <td class="price">
              [#if surplus < 0]
                ${complexWare.buyPrice} Cr
              [#else]
                -
              [/#if]
            </td>          
            <td class="price">
              [#if surplus > 0]
                ${complexWare.sellPrice} Cr
              [#else]
                -
              [/#if]
            </td>          
            <td class="profit">${complexWare.profit?round} Cr</td>
          </tr>
        [/#list]
        <tr>
          <td colspan="4"><hr /></td>
          <td colspan="3"><hr /></td>
        </tr>
        <tr>
          <th class="profit" colspan="6">Total</th>
          <td class="profit">${profit?round} Cr</td>          
        </tr>        
      </table>
    [/#if]
  </body>
</html>