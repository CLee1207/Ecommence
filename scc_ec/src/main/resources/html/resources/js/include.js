;(function(window, undefined){
	
	var document = window.document,
		body = document.body,
		node = document.getElementById('include');
	
	/* !! Include Javascript
	 * @ base: string
	 * @ scrips: array || string
	 * ** *** **** *** ** *
	 */
	window.include = function(base, scripts){
		
		var _ = this;
		
		_.doScript = function(url, callback){
			var script = document.createElement('script');
			callback = callback || new Function()
			,script.type = 'text/javascript'
			,script.language = 'javascript'
			,script.async = true
			,script.defer = true
			,script.src = url;
			
			script.onload = script.onreadystatechange = function(){
				callback( script );
			}
			body.appendChild( script );
		}
		
		base = base || '', scripts = scripts || '';
		
		if( typeof scripts === 'string' ){
			_.doScript(base + scripts + '.js');
			return;
		}
		
		if( scripts.length ){
			_.doCycle = function( scripts ){
				if( scripts && scripts.length ){
					this.doScript( base + scripts.shift() + '.js', function(script){
						_.doCycle( scripts );
					});
					return false;
				}
			}
			_.doCycle( scripts );
		}
		
	}
	
	// Execution
	if( node ){
		var config = node.getAttribute('data-config');
		window.include( config );
	}
	
})(window);