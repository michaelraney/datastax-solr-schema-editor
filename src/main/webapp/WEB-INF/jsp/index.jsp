<html>
<head>

    <link rel="stylesheet" href="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.css" />
    <script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
    <script src="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>

   <!-- <script type="text/javascript" src="https://code.jquery.com/jquery-1.10.2.min.js"></script>-->
    <script type="text/javascript" src="js/xonomy/xonomy.js"></script>
    <link type="text/css" rel="stylesheet" href="js/xonomy/xonomy.css"/>

    <script type="text/javascript" src="js/xonomy/solr/schemaHelper.js"></script>

     <script type="text/javascript">



/* Initialize Button Events and map to action*/
     $(document).ready(function(){
         $("#refreshSchema").click(function(){
                getSolrSchemaFromDSE($("#schemaLocation").val(), $("#schemaName").val(), $("#tableName").val())
         });

         $("#harvestSchema").click(function(){
                postToSolr();
         });

          $("#reloadCore").click(function(){
                 reloadCore();
          });

     });

    /* Harvest Schema from Xonomy and then post xml to solr*/
      function postToSolr(){

            var xml = Xonomy.harvest();

            var domain = $("#schemaLocation").val();

            var schemaName = $("#schemaName").val();

            var tableName = $("#tableName").val();

            $.ajax({
                    type: "POST" ,
                    url: "/uploadNewSchema",
                    data : { "xml" : xml,  "domain" : domain, "schema":schemaName, "table":tableName},
                    dataType: "text" ,
                    success: function(xml) {
                       console.log(xml);
                    }
                });
        }
        function reloadCore(){

            var domain = $("#schemaLocation").val();

            var schemaName = $("#schemaName").val();

            var tableName = $("#tableName").val();

            $.ajax({
                    type: "GET" ,
                    url: "/reloadCore",
                    data : {"domain" : domain, "schema":schemaName, "table":tableName},
                    dataType: "text" ,
                    success: function(xml) {
                       console.log(xml);
                    }
                });
        }
        function getSolrSchemaFromDSE(domain, schemaName, tableName) {


               var docSpec= buildSolrSchemaDocumentSpec();

               var editor=document.getElementById("editor");


                $.ajax({
                    type: "GET" ,
                    url: "/getSchemaFromAddress",
                    data : { "domain" : domain, "schema":schemaName, "table":tableName },
                    dataType: "text" ,
                    success: function(xml) {
                       console.log(xml);
                       Xonomy.render(xml.trim(), editor, docSpec);
                    }
                });


            }
        </script>
</head>
<body>

     <div id="canvisDiv" style="width:90%;padding:20px">

        <h3 class="ui-bar ui-bar-a ui-corner-all">Solr Schema Editor</h3>

        <div id="inputDiv">

            <table >
                <tr>
                    <td>
                        <label for="schemaLocation">Host</label>
                         <input id="schemaLocation"  name="schemaLocation" type="text" data-mini="true" data-clear-btn="false" value="http://localhost:8983" placeholder="http://localhost:8983">
                    </td>
                     <td >
                        <label for="schemaName">Schema Name</label>
                        <input id="schemaName" name="schemaName" type="text" data-mini="true" data-clear-btn="false"  value="" placeholder="mySchema">
                    </td>
                    <td >
                        <label for="tableName">Table Name</label>
                        <input id="tableName" name="tableName" type="text" data-mini="true" data-clear-btn="false" value="" placeholder="myTable">
                    </td>
                 </tr>
             </table>
               <hr>
               <table>
                <tr>

                    <td>
                     <button id="refreshSchema"  class="ui-btn ui-mini">Refresh Schema</button>
                    </td>
                    <td>
                     <button id="harvestSchema" class="ui-btn ui-mini">POST Schema</button>
                    </td>
                    <td>
                     <button id="reloadCore" class="ui-btn ui-mini">Reload Core</button>
                    </td>

                </tr>
                </table>
               <hr>

            </div>
            <div id="editor"></div>

    </div>


</body>
