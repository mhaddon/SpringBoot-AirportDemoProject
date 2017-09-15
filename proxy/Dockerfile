FROM nginx:alpine

COPY nginx.conf /etc/nginx/nginx.conf
COPY upstream.conf.template /etc/nginx/upstream.conf.template 
 
CMD /bin/sh -c "envsubst < /etc/nginx/upstream.conf.template > /etc/nginx/upstream.conf && nginx -g 'daemon off;'"
