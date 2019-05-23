angular.module("pontos",[])
.controller("PontosController",function($scope,$http){
	$scope.grid={};
	$scope.param={table:{resultsPerPage:20,sort:[],object:{tenant:TENANT},page:0}};
	$scope.getGrid=function(doKeepPage){
		if(!(doKeepPage)){
			$scope.param.table.page=0;
		}
		$scope.param.table.object.tenant=TENANT;
		if($scope.param.table.object.id<0||$scope.param.table.object.id=="")$scope.param.table.object.id=undefined;
		if($scope.param.table.object.nome=="")$scope.param.table.object.nome=undefined;
		if($scope.param.table.object.cidade=="")$scope.param.table.object.cidade=undefined;
		if($scope.param.table.object.endereco=="")$scope.param.table.object.endereco=undefined;
		if($scope.param.table.object.cep=="")$scope.param.table.object.cep=undefined;
		
		$http.post("/grid/"+TENANT+"/",$scope.param.table).then(function(response){
			$scope.grid=response.data;
		});
	}
	$scope.order=function(property){
		for(let x in $scope.param.table.sort){
			if($scope.param.table.sort[x].property==property){
				if($scope.param.table.sort[x].direction=='ASC'){
					$scope.param.table.sort[x].direction='DESC';
					$scope.getGrid(true);
					return;
				}else{
					$scope.param.table.sort.splice(x,1);
					$scope.getGrid(true);
					return;
				}
			}
		}
		$scope.param.table.sort.push({property:property,direction:'ASC'});
		$scope.getGrid(true);		
	};
	$scope.has=function(property,direction){
		for(let x in $scope.param.table.sort){
			if($scope.param.table.sort[x].property==property){
				if(direction){
					if($scope.param.table.sort[x].direction==direction){
						return true;
					}else{
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}

	$scope.first=function(){
		$scope.getGrid();				
	}
	$scope.previous=function(){
		if($scope.param.table.page>0)$scope.param.table.page--;
		$scope.getGrid(true);		
	}
	$scope.next=function(){
		if($scope.param.table.page<($scope.grid.totalPages-1))$scope.param.table.page++;
		$scope.getGrid(true);		
	}
	$scope.last=function(){
		$scope.param.table.page=($scope.grid.totalPages-1);
		$scope.getGrid(true);		
	}
	$scope.getGrid();
})