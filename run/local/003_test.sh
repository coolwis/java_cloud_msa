curl --header "Content-Type: application/json" \
--request POST \
--data '{"productId":9,"productName":"9_name","productInfo":"9_productInfo", "recommendList":[{"recommendId":91,"author":"author_recommend", "content":"content91"}],"reviewList":[{"reviewId":1,"author":"author1","subject":"sub1","content":"content1"}]}' \
http://localhost:7000/composite


curl http://localhost:7000/composite/9| jq

curl -X "DELETE" http://localhost:7000/composite/9