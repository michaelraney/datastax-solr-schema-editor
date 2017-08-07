
function buildSolrSchemaDocumentSpec(){

     var TypeClassList = ["org.apache.solr.schema.StrField",
                                "org.apache.solr.schema.TrieDateField",
                                "org.apache.solr.schema.BoolField"];

     var TypeNameList = ["StrField",
                         "TrieDateField",
                         "BoolField"];


     var BoolOptionList = ["true", "false" ];

     var fieldTypeSurrogateDefinition = { menu: [{
        caption: "Add @class=\"someClassName\"", action: Xonomy.newAttribute, actionParameter: {name: "class", value: "someClassName"}, hideIf: function(jsElement){ return jsElement.hasAttribute("class"); }},{
        caption: "Add @name=\"someTypeName\"", action: Xonomy.newAttribute, actionParameter: {name: "name", value: "someTypeName"}, hideIf: function(jsElement){ return jsElement.hasAttribute("name"); }}, {
        caption: "Delete this <fieldType>", action: Xonomy.deleteElement},{
        caption: "New <fieldType> before this", action: Xonomy.newElementBefore, actionParameter: "<fieldType/>" },{
        caption: "New <fieldType> after this", action: Xonomy.newElementAfter, actionParameter: "<fieldType/>"}],
        attributes: {"class": { asker: Xonomy.askOpenPicklist, askerParameter: TypeClassList, menu: [{caption: "Delete this @class", action: Xonomy.deleteAttribute}]},
                       "name":{ asker: Xonomy.askOpenPicklist, askerParameter: TypeNameList, menu: [{caption: "Delete this @name", action: Xonomy.deleteAttribute}]}}
      };


      var fieldSurrogateDefinition = { menu: [{caption: "Add @name=\"someFieldName\"", action: Xonomy.newAttribute, actionParameter: {name: "name", value: "someFieldName"}, hideIf: function(jsElement){ return jsElement.hasAttribute("name"); }},{
         caption: "Add @type=\"someType\"", action: Xonomy.newAttribute, actionParameter: {name: "type", value: "someType"}, hideIf: function(jsElement){ return jsElement.hasAttribute("type"); }},{
         caption: "Add @indexed=\"true\"",  action: Xonomy.newAttribute, actionParameter: {name: "indexed", value: "true"}, hideIf: function(jsElement){ return jsElement.hasAttribute("indexed"); }},{
          caption: "Add @stored=\"true\"", action: Xonomy.newAttribute, actionParameter: {name: "stored", value: "true"}, hideIf: function(jsElement){ return jsElement.hasAttribute("stored"); }},{
          caption: "Add @multiValued=\"true\"", action: Xonomy.newAttribute, actionParameter: {name: "multiValued", value: "true"}, hideIf: function(jsElement){ return jsElement.hasAttribute("multiValued"); }},{
          caption: "Add @docValues=\"true\"", action: Xonomy.newAttribute, actionParameter: {name: "docValues", value: "true"}, hideIf: function(jsElement){ return jsElement.hasAttribute("docValues"); }},{

          caption: "Delete this <field>", action: Xonomy.deleteElement},{
          caption: "New <field> before this", action: Xonomy.newElementBefore, actionParameter: "<field/>" },{
          caption: "New <field> after this", action: Xonomy.newElementAfter, actionParameter: "<field/>"}],

           attributes: { "name":{ asker: Xonomy.askString, menu: [{caption: "Delete this @name", action: Xonomy.deleteAttribute}]},
                      "type":{ asker: Xonomy.askOpenPicklist, askerParameter: TypeNameList, menu: [{caption: "Delete this @type", action: Xonomy.deleteAttribute}]},
                      "indexed":{ asker: Xonomy.askPicklist, askerParameter: BoolOptionList, menu: [{caption: "Delete this @indexed", action: Xonomy.deleteAttribute}]},
                      "stored":{ asker: Xonomy.askPicklist, askerParameter: BoolOptionList, menu: [{caption: "Delete this @stored", action: Xonomy.deleteAttribute}]},
                      "multiValued":{ asker: Xonomy.askPicklist, askerParameter: BoolOptionList, menu: [{caption: "Delete this @multiValued", action: Xonomy.deleteAttribute}]},
                      "docValues":{ asker: Xonomy.askPicklist, askerParameter: BoolOptionList, menu: [{caption: "Delete this @docValues", action: Xonomy.deleteAttribute}]}
                      }
     };


        var docSpec={
           onchange: function(){
                console.log("I been changed now!") },
       validate: function(obj){
              console.log("I be validatin' now!")},


               elements: {
                     "schema": { menu: [
                                       {caption: "Add @name=\"mySchemaName\"", action: Xonomy.newAttribute, actionParameter: {name: "name", value: "mySchemaName"}, hideIf: function(jsElement){ return jsElement.hasAttribute("name"); }},
                                       {caption: "Add @version=\"1.5\"", action: Xonomy.newAttribute, actionParameter: {name: "version", value: "1.5"}, hideIf: function(jsElement){ return jsElement.hasAttribute("version"); }},
                                       {caption: "Add a new <uniqueKey>", action: Xonomy.newElementChild, actionParameter: "<uniqueKey>...</uniqueKey>" , hideIf: function(jsElement){ return jsElement.hasChildElement("uniqueKey"); }},
                                       {caption: "Add a new <defaultSearchField>", action: Xonomy.newElementChild, actionParameter: "<defaultSearchField>...</defaultSearchField>" , hideIf: function(jsElement){ return jsElement.hasChildElement("defaultSearchField"); }}],
                                       attributes: {"name": { asker: Xonomy.askString, menu: [{caption: "Delete this @name", action: Xonomy.deleteAttribute}]},
                                                                "version":{ asker: Xonomy.askString, menu: [{caption: "Delete this @version", action: Xonomy.deleteAttribute}]}}

                               },
                         "types": { menu: [{ caption: "Add a new <fieldType>", action: Xonomy.newElementChild, actionParameter: "<fieldType/>" }] },
                         "fieldType": fieldTypeSurrogateDefinition,
                         "defaultSearchField": { menu: [{ caption: "Delete this @defaultSearchField", action: Xonomy.deleteElement}]},
                         "fields": { menu: [{ caption: "Add a new <field>", action: Xonomy.newElementChild, actionParameter: "<field/>" }] },
                         "field": fieldSurrogateDefinition}};


        return docSpec;

}